// Listen for storage events from main.js
let notificationData = null;

window.addEventListener('storage', (event) => {
    console.log("Storage event detected:", event);
    if (event.key === 'newMessageNotification') {
        notificationData = JSON.parse(event.newValue);
        if (notificationData) {
            console.log("New message notification received:", notificationData);

            // Get the current user ID (assuming it's stored in sessionStorage or localStorage)
            const currentUserId = sessionStorage.getItem('username');

            // Check if the notification is for the current user
            if (notificationData.recipientId === currentUserId) {
                showNotification(notificationData.message, 'message');
                updateNavbarMessageNotification();
            }
        }
    }
});

async function fetchNotifications() {
    try {
        const currentUserId = sessionStorage.getItem('username');
        const response = await fetch(`/notifications/${currentUserId}`, { credentials: 'include' });
        if (response.ok) {
            const notifications = await response.json();
            notifications.forEach(notification => {
                showNotification(notification.message, notification.type);
            });
            updateNavbarMessageNotification(notifications.length); // Update the badge with the correct count
        }
    } catch (error) {
        console.error("Failed to fetch notifications:", error);
    }
}

// Function to display notification
function showNotification(message, type = 'info', timeout = 5000) {
    const notification = document.createElement('div');
    notification.classList.add('notification', type);
    notification.textContent = message;

    const notificationContainer = document.getElementById('notification-container');
    notificationContainer.appendChild(notification);

    // Auto-remove notification after the timeout
    setTimeout(() => {
        notification.remove();
    }, timeout);
}

// Update notification icon on the navbar with the count of unread notifications
function updateNavbarMessageNotification(count = 0) {
    const notificationBadge = document.getElementById('message-notification');
    if (notificationBadge) {
        if (count > 0) {
            notificationBadge.style.display = 'inline';
            notificationBadge.textContent = count;
        } else {
            notificationBadge.style.display = 'none';
        }
    }
}

async function clearNotificationBadge() {
    const notificationBadge = document.getElementById('message-notification');
    const notificationContainer = document.getElementById('notification-container');

    // Hide the badge and reset the count
    if (notificationBadge) {
        notificationBadge.style.display = 'none';
        notificationBadge.textContent = '0';
    }

    // Clear the notifications in the notification container
    if (notificationContainer) {
        notificationContainer.innerHTML = ''; // Remove all displayed notifications
    }

    // Get recipient ID from session storage or another source
    const currentUserId = sessionStorage.getItem('username');
    console.log("Attempting to clear notifications for:", currentUserId); // Debug log
    console.log(`Request URL: /notifications/${currentUserId}/mark-as-read`);
    const csrfToken = document.getElementById("csrf-token").value;
    // Change method from GET to PUT
    try {
        const response = await fetch(`/notifications/${currentUserId}/mark-as-read`, {
            method: 'PUT',
            credentials: 'include',
            headers: {
                'X-CSRF-TOKEN': csrfToken
            }
        });
        if (response.ok) {
            console.log("Notifications marked as read.");
        } else {
            console.error("Failed to mark notifications as read.");
        }
    } catch (error) {
        console.error("Error while marking notifications as read:", error);
    }
}

// DOMContentLoaded event for UI interactions
document.addEventListener("DOMContentLoaded", function () {
    console.log("Navbar loaded and ready.");

    const avatarIcon = document.getElementById("avatar-icon");
    const dropdownMenu = document.getElementById("dropdown-menu");
    const notificationBadge = document.getElementById('message-notification');

    // if (notificationBadge) {
    //     notificationBadge.addEventListener('click', clearNotificationBadge);
    // }

    // Toggle dropdown menu on avatar click
    avatarIcon.addEventListener("click", function () {
        dropdownMenu.style.display = dropdownMenu.style.display === "block" ? "none" : "block";
    });

    // Close dropdown if clicked outside
    document.addEventListener("click", function (event) {
        if (!avatarIcon.contains(event.target) && !dropdownMenu.contains(event.target)) {
            dropdownMenu.style.display = "none";
        }
    });

    // Fetch notifications on page load
    fetchNotifications();
});
