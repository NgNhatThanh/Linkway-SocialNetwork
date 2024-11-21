'use strict';

// Select page elements
const usernamePage = document.querySelector('#username-page');
const chatPage = document.querySelector('#chat-page');
const messageForm = document.querySelector('#messageForm');
const messageInput = document.querySelector('#message');
const connectingElement = document.querySelector('.connecting');
const chatArea = document.querySelector('#chat-messages');
const logout = document.querySelector('#logout');
const usernameForm = document.querySelector('#usernameForm');
const errorMessageElement = document.getElementById('error-message');
const loadingTextElement = document.getElementById('loading-text');

// Notifications container
// const notificationContainer = document.createElement('div');
// notificationContainer.classList.add('notifications');
// document.body.appendChild(notificationContainer);

let stompClient = null;
let username = null;
let displayName = null;
let selectedUserId = null; // Placeholder for selected user ID
let selectedUserName = null;

// Fetch recipientId from the URL or another source
// const urlParams = new URLSearchParams(window.location.search);
// const recipientId = urlParams.get('recipientId'); // Get recipientId from URL query parameters

// Fetch current user from session
async function fetchCurrentUser() {
    try {
        const response = await fetch('/current-user', { credentials: 'include' });
        if (response.ok) {
            const currentUser = await response.json();
            if (currentUser) {
                username = currentUser.username;
                displayName = currentUser.displayName;
                sessionStorage.setItem('username', username);
                sessionStorage.setItem('displayName', displayName);
                // document.querySelector('#connected-user-displayname').textContent = displayName;
                connect();
                fetchRecentUserChatWith();
            } else {
                showError('No user data returned.');
            }
        } else {
            showError(response.status === 401 ? 'Unauthorized: Please log in to continue.' : `Failed to retrieve user information. Status: ${response.status}`);
        }
    } catch (error) {
        console.error('Error fetching user data:', error);
        showError('An error occurred while retrieving user information.');
    }
}

// Show error message and optionally redirect if unauthorized
function showError(message) {
    loadingTextElement.style.display = 'none';
    errorMessageElement.textContent = message;
    errorMessageElement.style.display = 'block';
    if (message === 'Unauthorized: Please log in to continue.') {
        window.location.href = '/login';
    }
}

// Connect to WebSocket server
function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    if (username && displayName && usernamePage && chatPage) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
    }
}

// On successful connection, subscribe and register the user
function onConnected() {
    stompClient.subscribe(`/user/${username}/queue/messages`, onMessageReceived);
    stompClient.subscribe(`/user/public`, onPublicMessageReceived);
    stompClient.subscribe(`/user/${username}/notify`, onNotiReceived);
    stompClient.send("/app/user.updateStatus", {}, JSON.stringify({ username, status: 'ONLINE' }));
}

// Handle WebSocket connection errors
function onError(error) {
    console.error('WebSocket error:', error);
    showError('Could not connect to WebSocket server. Please refresh the page and try again.');
    connectingElement.classList.add('hidden');
}

// Fetch data for the current recipient (selected user)
async function fetchCurrentRecipient(recipientId) {
    try {
        const response = await fetch(`/users/${recipientId}`);
        if (!response.ok) {
            throw new Error('Failed to fetch recipient data');
        }
        const user = await response.json();
        selectedUserName = user.displayName; // Store the selected user's name
        updateChatHeader(user);
    } catch (error) {
        console.error('Error fetching recipient data:', error);
        showError('Failed to fetch recipient information.');
    }
}
// Append a reliable user to the reliable users list and add event listeners
async function appendReliableUserElement(user, reliableUsersList) {
    const listItem = document.createElement('li');
    listItem.classList.add('reliable-item');
    listItem.id = user.username;

    const userImage = document.createElement('img');
    userImage.src = `http://localhost:8080${user.avatarImagePath}`;

    const usernameSpan = document.createElement('span');
    usernameSpan.textContent = user.displayName;

    listItem.appendChild(userImage);
    listItem.appendChild(usernameSpan);

    // Add an event listener for clicking the reliable user item
    listItem.addEventListener('click', async () => {
        selectedUserName = user.displayName; // Store the selected user's name
        selectedUserId = user.username; // Store the selected user's username
        loadMessageHistory(selectedUserId); // Fetch message history for selected user
        updateChatHeader(); // Update the chat header with the selected user's name
        messageInput.focus();
    });

    reliableUsersList.appendChild(listItem);
}

async function fetchRecentUserChatWith() {
    try {
        // Fetch the list of users the current user has chatted with
        const responseUsers = await fetch(`/users/chat`);
        // Fetch the list of users with unread notifications
        const responseUnread = await fetch(`/message/notifications/${username}`);

        if (responseUsers.ok && responseUnread.ok) {
            const recentUsers = await responseUsers.json();
            const unreadNotifications = await responseUnread.json();

            // Extract sender IDs with unread notifications
            const unreadSenderIds = new Set(unreadNotifications.map(notification => notification.senderId));

            const recentUsersList = document.getElementById('RecentUsers');
            recentUsersList.innerHTML = '';

            if (recentUsers && recentUsers.length > 0) {
                recentUsers.forEach(user =>
                    appendRecentUserElement(user, recentUsersList, unreadSenderIds.has(user.username))
                );
            } else {
                recentUsersList.innerHTML = '<li>No users followed yet.</li>';
            }
        } else {
            showError('Failed to fetch reliable users or unread notifications');
        }
    } catch (error) {
        console.error('Error fetching user data:', error);
        showError('An error occurred while retrieving user information.');
    }
}

function appendRecentUserElement(user, recentUsersList, hasUnread) {
    const listItem = document.createElement('li');
    listItem.classList.add('Recent-item');
    listItem.id = user.username;
    listItem.textContent = user.displayName;
    listItem.style.backgroundImage = `url(http://localhost:8080${user.avatarImagePath})`;

    // Highlight the user if they have unread notifications
    if (hasUnread) {
        listItem.classList.add('highlight');
    }

    // Add a click event listener
    listItem.addEventListener('click', async () => {
        selectedUserName = user.displayName;
        selectedUserId = user.username;
        updateChatHeader();
        loadMessageHistory(selectedUserId);
        messageInput.focus();

        // Attempt to mark notifications for the selected user as read
        try {
            const csrfToken = document.getElementById("csrf-token").value;
            const response = await fetch(`/message/notifications/${user.username}/${username}/mark-as-read`, {
                method: 'PUT',
                credentials: 'include',
                headers: {
                    'X-CSRF-TOKEN': csrfToken
                }
            });

            if (response.ok) {
                console.log(`Notifications for ${selectedUserId} marked as read.`);
                listItem.classList.remove('highlight'); // Remove the highlight if successful
            } else {
                console.error(`Failed to mark notifications for ${selectedUserId} as read. Status: ${response.status}`);
            }
        } catch (error) {
            console.error("Error while marking notifications as read:", error);
        }
        // Remove the highlight class when clicked
        listItem.classList.remove('highlight');
    });

    // Append the list item to the recent users list
    recentUsersList.appendChild(listItem);
}



// Fetch and display the list of following users
async function fetchFollowingUsers() {
    try {
        const response = await fetch(`/users/following/${username}`);
        const followingUsers = await response.json();
        const followingUsersList = document.getElementById('followingUsers');
        followingUsersList.innerHTML = '';

        if (followingUsers && followingUsers.length > 0) {
            followingUsers.forEach(user => appendFollowingUserElement(user, followingUsersList));
        } else {
            followingUsersList.innerHTML = '<li>No users followed yet.</li>';
        }
    } catch (error) {
        console.error('Error fetching following users:', error);
        showError('Failed to load following users.');
    }
}

// Append a user to the following list
function appendFollowingUserElement(user, followingUsersList) {
    const listItem = document.createElement('li');
    listItem.classList.add('following-item');
    listItem.id = user.username;

    // lòad user avatar
    const userImage = document.createElement('img');
    userImage.src = `http://localhost:8080${user.avatarImagePath}`;

    const usernameSpan = document.createElement('span');
    usernameSpan.textContent = user.displayName;

    listItem.appendChild(userImage);
    listItem.appendChild(usernameSpan);

    listItem.addEventListener('click', () => {
        selectedUserName = user.displayName; // Store the selected user's name
        selectedUserId = user.username;
        updateChatHeader();
        loadMessageHistory(selectedUserId); // Fetch message history for selected user
        messageInput.focus();
    });

    followingUsersList.appendChild(listItem);
}

// Update the chat header with the name of the user you're chatting with
function updateChatHeader() {
    const chatHeader = document.getElementById('chat-with-username');
    if (selectedUserName) {
        chatHeader.textContent = selectedUserName;
    } else {
        chatHeader.textContent = 'Select a user to chat with';
    }
}


// // Initialize chat with the recipient from the URL
// function initializeChatWithRecipient() {
//     const recipientInfo = document.getElementById('recipientInfo');
//     const recipientId = recipientInfo ? recipientInfo.getAttribute('data-recipient-id') : null;
//     const recipientName = recipientInfo ? recipientInfo.getAttribute('data-recipient-name') : null;

//     if (recipientId && recipientName) {
//         // Set selected user details
//         selectedUserId = recipientId;
//         selectedUserName = recipientName;

//         // Update the chat header
//         updateChatHeader();

//         // Load message history for the selected user
//         loadMessageHistory(selectedUserId);

//         // Set focus on the message input field
//         const messageInput = document.getElementById('messageInput'); // Ensure this element ID is correct
//         if (messageInput) {
//             messageInput.focus();
//         }
//     } else {
//         console.error("Recipient information not found.");
//     }
// }

// Load message history for selected user
async function loadMessageHistory(recipientId) {
    try {
        const response = await fetch(`/messages/${username}/${recipientId}`);
        if (!response.ok) {
            throw new Error(`Error fetching messages: ${response.status}`);
        }
        const messages = await response.json();

        if (Array.isArray(messages)) {
            chatArea.innerHTML = ''; // Clear chat area before loading message history
            messages.forEach(message => {
                appendMessageToChat(message, message.senderId === username);
            });
        } else {
            console.error('Unexpected response format:', messages);
            showError('Failed to load message history. The response format is incorrect.');
        }
    } catch (error) {
        console.error('Error fetching message history:', error);
        showError('Failed to load message history.');
    }
}

// Send a chat message
function sendMessage(event) {
    event.preventDefault();
    const messageContent = messageInput.value.trim();

    if (messageContent && stompClient && selectedUserId) {
        const chatMessage = {
            senderId: username,
            recipientId: selectedUserId,
            content: messageContent,
            type: 'CHAT'
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
        messageInput.focus();
        fetchRecentUserChatWith();
        appendMessageToChat(chatMessage, true);
        const notificationData = {
            senderId: chatMessage.senderId,
            recipientId: chatMessage.recipientId,
            message: `New message from ${chatMessage.senderId}`
        };
        localStorage.setItem('newMessageNotification', JSON.stringify(notificationData));
    } else {
        console.log('Message cannot be empty or no recipient selected');
    }
}

// Append message to the chat area
// Append message to the chat area with different alignment for sent and received messages
function appendMessageToChat(messageData, isSent) {
    const chatMessage = document.createElement('div');
    chatMessage.classList.add('chat-message');

    // Add the 'sent' class for sender's message, 'received' for reply's message
    chatMessage.classList.add(isSent ? 'sent' : 'received');

    // Create the message content element
    const messageContent = document.createElement('div');
    messageContent.classList.add('message-content');
    messageContent.textContent = `${isSent ? 'You' : messageData.senderId}: ${messageData.content}`;

    // Append message content to the chat message div
    chatMessage.appendChild(messageContent);

    // Add the chat message to the chat area
    chatArea.appendChild(chatMessage);
    chatArea.scrollTop = chatArea.scrollHeight;
}

// Display a notification with a timeout
function showNotification(message, type = 'info', timeout = 5000) {
    // Create the notification element
    const notification = document.createElement('div');
    notification.classList.add('notification', type);
    notification.textContent = message;

    // Append the notification to the notification container
    const notificationContainer = document.getElementById('notification-container');
    notificationContainer.appendChild(notification);

    // Automatically remove the notification after the timeout period
    setTimeout(() => {
        notification.remove();
    }, timeout);
}



// Logout and disconnect from WebSocket
function onLogout() {
    if (stompClient) {
        stompClient.send("/app/user.updateStatus", {}, JSON.stringify({ username, status: 'OFFLINE' }));
        stompClient.disconnect(() => {
            console.log('Disconnected from WebSocket');
            fetchOnlineUsers(); // Refresh the online users list after logout
        });
    }
    usernamePage.classList.remove('hidden');
    chatPage.classList.add('hidden');
}

function highlightUser(username) {
    const recentUserElement = document.getElementById(username);
    const followingUserElement = document.querySelector(`#followingUsers #${username}`);

    if (recentUserElement) recentUserElement.classList.add('highlight');
    if (followingUserElement) followingUserElement.classList.add('highlight');
}

async function fetchMessageNotifications() {
    try {
        const currentUserId = sessionStorage.getItem('username');
        const response = await fetch(`/message/notifications/${currentUserId}`, { credentials: 'include' });
        if (response.ok) {
            const notifications = await response.json();
            // notifications.forEach(notification => {
            //     showNotification(notification.message, notification.type);
            // });
            updateNavbarMessageNotification(notifications.length); // Update the badge with the correct count
        }
    } catch (error) {
        console.error("Failed to fetch notifications:", error);
    }
}

// Handle message received event
function onMessageReceived(message) {
    const messageData = JSON.parse(message.body);
    const chatPage = document.getElementById("chat-page");
    if (chatPage) {
        appendMessageToChat(messageData, false);
    }
    showNotification(`New message from ${messageData.senderId}`, 'message');
    highlightUser(messageData.senderId);
    fetchMessageNotifications();

    // Lưu thông báo vào localStorage để truyền thông tin sang navbar.js
    const notificationData = {
        senderId: messageData.senderId,
        recipientId: messageData.recipientId,
        message: `New message from ${messageData.senderId}`
    };
    localStorage.setItem('newMessageNotification', JSON.stringify(notificationData));
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

// Handle public messages
function onPublicMessageReceived(message) {
    const messageData = JSON.parse(message.body);
    const chatMessage = document.createElement('div');
    chatMessage.classList.add('chat-message');
    chatMessage.textContent = `Public: ${messageData.content}`;
    chatArea.appendChild(chatMessage);
    chatArea.scrollTop = chatArea.scrollHeight;
    showNotification('New public message', 'public');
}

document.addEventListener("DOMContentLoaded", async () => {
    fetchCurrentUser();
    const chatPage = document.getElementById("chat-page");
    const recipientId = chatPage.getAttribute("data-recipient-id");
    // Fetch the current user before proceeding with chat setup
    then(() => {
        if (recipientId) {
            console.log("Recipient ID:", recipientId);

            // Initialize the chat with the specific recipient
            selectedUserId = recipientId;
            fetchCurrentRecipient(selectedUserId); // Fetch recipient data
            loadMessageHistory(selectedUserId);  // Assuming loadMessageHistory takes recipientId
            updateChatHeader(); // Update the chat header to show recipient's name
            messageInput.focus();
        } else {
            // Fetch the list of following users if no specific recipient is selected
            fetchRecentUserChatWith();
        }
    }).catch((error) => {
        console.error("Error fetching current user:", error);
    });
});
messageForm.addEventListener('submit', sendMessage);
logout.addEventListener('click', onLogout);
// fetchCurrentUser();

function onNotiReceived(noti){
    const content = JSON.parse(noti.body).content;

    const pp = document.createElement('p');
    pp.textContent = content;

    document.body.appendChild(pp);
}