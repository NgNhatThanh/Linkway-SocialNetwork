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
                document.querySelector('#connected-user-displayname').textContent = displayName;
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
    if (username && displayName) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
        const socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);
        stompClient.connect({}, onConnected, onError);
    }
}

// On successful connection, subscribe and register the user
function onConnected() {
    stompClient.subscribe(`/user/${username}/queue/messages`, onMessageReceived);
    stompClient.subscribe(`/user/public`, onPublicMessageReceived);
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

// Fetch data for the current recipient (selected user)
async function fetchRecentUserChatWith() {
    try {
        const response = await fetch(`/users/chat`);
        if (response.ok) {
            const RecentUsers = await response.json();
            const RecentUsersList = document.getElementById('RecentUsers');
            RecentUsersList.innerHTML = '';
            if (RecentUsers && RecentUsers.length > 0) {
                RecentUsers.forEach(user => appendRecentUserElement(user, RecentUsersList));
            } else {
                RecentUsersList.innerHTML = '<li>No users followed yet.</li>';
            }
        } else {
            showError('Failed to fetch reliable users');
        }
    }
    catch (error) {
        console.error('Error fetching user data:', error);
        showError('An error occurred while retrieving user information.');
    }
}
// Append a reliable user to the reliable users list and add event listeners
function appendRecentUserElement(user, RecentUsersList) {
    const listItem = document.createElement('li');
    listItem.classList.add('Recent-item');
    listItem.id = user.username;
    listItem.textContent = user.displayName;
    listItem.style.backgroundImage = `url(http://localhost:8080${user.avatarImagePath})`;
    listItem.addEventListener('click', () => {
        selectedUserName = user.displayName;
        selectedUserId = user.username;
        updateChatHeader();
        loadMessageHistory(selectedUserId);
        messageInput.focus();
        // Remove the highlight class when clicked
        listItem.classList.remove('highlight');
    });
    RecentUsersList.appendChild(listItem);

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

// Handle message received event
function onMessageReceived(message) {
    const messageData = JSON.parse(message.body);
    appendMessageToChat(messageData, false);
    showNotification(`New message from ${messageData.senderId}`, 'message');
    highlightUser(messageData.senderId);
    // Update the navbar notification indicator
    updateNavbarMessageNotification();
}

// Function to update navbar message notification
function updateNavbarMessageNotification() {
    const notificationBadge = document.getElementById('message-notification');

    // If badge exists, show it and increment count
    if (notificationBadge) {
        notificationBadge.style.display = 'inline';
        const count = parseInt(notificationBadge.textContent) || 0;
        notificationBadge.textContent = count + 1;
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

document.addEventListener("DOMContentLoaded", () => {
    const chatPage = document.getElementById("chat-page");
    const recipientId = chatPage.getAttribute("data-recipient-id");

    // Fetch the current user before proceeding with chat setup
    fetchCurrentUser().then(() => {
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
