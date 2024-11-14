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
const notificationContainer = document.createElement('div');
notificationContainer.classList.add('notifications');
document.body.appendChild(notificationContainer);

let stompClient = null;
let username = null;
let displayName = null;
let selectedUserId = null; // Placeholder for selected user ID

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
                fetchFollowingUsers();
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

    const userImage = document.createElement('img');
    userImage.src = `http://localhost:8080${user.avatarImagePath}`;

    const usernameSpan = document.createElement('span');
    usernameSpan.textContent = user.displayName;

    listItem.appendChild(userImage);
    listItem.appendChild(usernameSpan);

    listItem.addEventListener('click', () => {
        selectedUserId = user.username;
        loadMessageHistory(selectedUserId); // Fetch message history for selected user
        messageInput.focus();
    });

    followingUsersList.appendChild(listItem);
}

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


// Display a notification
function showNotification(message, type = 'info') {
    const notification = document.createElement('div');
    notification.classList.add('notification', type);
    notification.textContent = message;
    notificationContainer.appendChild(notification);

    // Automatically remove the notification after 5 seconds
    setTimeout(() => {
        notificationContainer.removeChild(notification);
    }, 5000);
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

// Handle message received event
function onMessageReceived(message) {
    const messageData = JSON.parse(message.body);
    appendMessageToChat(messageData, false);
    showNotification(`New message from ${messageData.senderId}`, 'message');
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

messageForm.addEventListener('submit', sendMessage);
logout.addEventListener('click', onLogout);
fetchCurrentUser();
