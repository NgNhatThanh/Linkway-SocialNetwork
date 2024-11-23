'use strict';
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


let stompClient = null;
let username = null;
let displayName = null;
let selectedUserId = null;
let selectedUserName = null;


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

function showError(message) {
    loadingTextElement.style.display = 'none';
    errorMessageElement.textContent = message;
    errorMessageElement.style.display = 'block';
    if (message === 'Unauthorized: Please log in to continue.') {
        window.location.href = '/login';
    }
}

function connect() {
    const socket = new SockJS('/ws');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, onConnected, onError);
    if (username && displayName && usernamePage && chatPage) {
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');
    }
}

function onConnected() {
    stompClient.subscribe(`/user/${username}/queue/messages`, onMessageReceived);
    stompClient.send("/app/user.updateStatus", {}, JSON.stringify({ username, status: 'ONLINE' }));
}

function onError(error) {
    console.error('WebSocket error:', error);
    showError('Could not connect to WebSocket server. Please refresh the page and try again.');
    connectingElement.classList.add('hidden');
}

async function fetchCurrentRecipient(recipientId) {
    try {
        const response = await fetch(`/users/${recipientId}`);
        if (!response.ok) {
            throw new Error('Failed to fetch recipient data');
        }
        const user = await response.json();
        selectedUserName = user.displayName;
        updateChatHeader(user);
    } catch (error) {
        console.error('Error fetching recipient data:', error);
        showError('Failed to fetch recipient information.');
    }
}

async function fetchRecentUserChatWith() {
    try {
        const responseUsers = await fetch(`/users/chat`);
        const responseUnread = await fetch(`/message/notifications/${username}`);

        if (responseUsers.ok && responseUnread.ok) {
            const recentUsers = await responseUsers.json();
            const unreadNotifications = await responseUnread.json();
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
    const listItem = document.createElement('div');
    listItem.classList.add('Recent-item');
    listItem.id = user.username;
    listItem.textContent = user.displayName;
    listItem.innerHTML = `<img src="${user.avatarImagePath}" alt="${user.displayName}" class="profile-image" style = "width: 50px; height: 50px; border-radius: 50%; margin-right: 10px;">`;
    listItem.innerHTML += `<span>${user.displayName}</span>`;

    if (hasUnread) {
        listItem.classList.add('highlight');
    }

    listItem.addEventListener('click', async () => {
        selectedUserName = user.displayName;
        selectedUserId = user.username;
        updateChatHeader();
        document.getElementById('messageForm').style = "display: block";
        loadMessageHistory(selectedUserId);
        messageInput.focus();

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
                listItem.classList.remove('highlight');
            } else {
                console.error(`Failed to mark notifications for ${selectedUserId} as read. Status: ${response.status}`);
            }

            chatArea.scrollTop = chatArea.scrollHeight;
        } catch (error) {
            console.error("Error while marking notifications as read:", error);
        }
        listItem.classList.remove('highlight');
    });

    recentUsersList.appendChild(listItem);
}

function updateChatHeader() {
    const chatHeader = document.getElementById('chat-with-username');
    if (selectedUserName) {
        chatHeader.textContent = selectedUserName;
    } else {
        chatHeader.textContent = 'Select a user to chat with';
    }
}

var lastMessageId = -1;
var isFetchingMoreMessages = false;

async function loadMessageHistory(recipientId, more = false) {
    if (!more) {
        lastMessageId = -1;
        isFetchingMoreMessages = false;
    }
    try {
        const api = `/messages/${username}/${recipientId}?lastMessageId=${lastMessageId}`;
        const response = await fetch(api);
        if (!response.ok) {
            throw new Error(`Error fetching messages: ${response.status}`);
        }
        const messages = await response.json();

        if (Array.isArray(messages)) {
            if (!more) chatArea.innerHTML = '';
            messages.forEach(message => {
                lastMessageId = message.id;
                addMessageToChat(message, message.senderId === username, true);
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

function sendMessage(event) {
    event.preventDefault();
    const messageContent = messageInput.value.trim();
    const imagesContainer = document.getElementById('images-container');
    if (imagesContainer.children.length === 0 && !messageContent) {
        console.log('Message cannot be empty or no recipient selected');
        return;
    }
    const sendTime = new Date();
    if (messageContent) {
        const chatMessage = {
            senderId: username,
            recipientId: selectedUserId,
            content: messageContent,
            type: 'text',
            sentAt: sendTime
        };

        stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
        messageInput.value = '';
        messageInput.focus();
        addMessageToChat(chatMessage, true);
        const notificationData = {
            senderId: chatMessage.senderId,
            recipientId: chatMessage.recipientId,
            message: `New message from ${chatMessage.senderId}`
        };
        localStorage.setItem('newMessageNotification', JSON.stringify(notificationData));
    }

    if (imagesContainer.children.length > 0) {
        console.log("Child: " + imagesContainer.children.length);
        for (let imageContainer of imagesContainer.children) {
            const chatMessage = {
                senderId: username,
                recipientId: selectedUserId,
                content: imageContainer.querySelector('.message-preview-img').src,
                type: 'media',
                sentAt: sendTime
            };
            stompClient.send("/app/chat.sendMessage", {}, JSON.stringify(chatMessage));
            addMessageToChat(chatMessage, true);
            const notificationData = {
                senderId: chatMessage.senderId,
                recipientId: chatMessage.recipientId,
                message: `New message from ${chatMessage.senderId}`
            };
            localStorage.setItem('newMessageNotification', JSON.stringify(notificationData));
        }
        imagesContainer.innerHTML = '';
    }
    fetchRecentUserChatWith();
}

function addMessageToChat(messageData, isSent, prepend = false) {
    const chatMessage = document.createElement('div');
    chatMessage.classList.add('chat-message');

    chatMessage.classList.add(isSent ? 'sent' : 'received');

    const messageContent = document.createElement('div');
    messageContent.classList.add('message-content');

    switch (messageData.type) {
        case 'text':
            messageContent.textContent = `${messageData.content}`;
            break;
        case 'media':
            const img = document.createElement('img');
            img.src = messageData.content;
            img.className = 'message-img';
            img.onclick = () => showPopupImage(img);
            messageContent.appendChild(img);
            break;
    }

    chatMessage.appendChild(messageContent);
    chatMessage.title = formatDate(new Date(messageData.sentAt));


    if (!prepend) chatArea.appendChild(chatMessage);
    else chatArea.prepend(chatMessage);
    chatArea.scrollTop = chatArea.scrollHeight;
}

function showNotification(message, type = 'info', timeout = 5000) {
    const notification = document.createElement('div');
    notification.classList.add('notification', type);
    notification.textContent = message;

    const notificationContainer = document.getElementById('notification-container');
    notificationContainer.appendChild(notification);

    setTimeout(() => {
        notification.remove();
    }, timeout);
}


function onLogout() {
    if (stompClient) {
        stompClient.send("/app/user.updateStatus", {}, JSON.stringify({ username, status: 'OFFLINE' }));
        stompClient.disconnect(() => {
            console.log('Disconnected from WebSocket');
            fetchOnlineUsers();
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

function onMessageReceived(message) {
    const messageData = JSON.parse(message.body);
    const chatPage = document.getElementById("chat-page");

    console.log(messageData);
    if (chatPage && selectedUserId === messageData.senderId) {
        addMessageToChat(messageData, false);
    }
    showNotification(`New message from ${messageData.senderId}`, 'message');
    highlightUser(messageData.senderId);

    const notificationData = {
        senderId: messageData.senderId,
        recipientId: messageData.recipientId,
        message: `New message from ${messageData.senderId}`
    };
    localStorage.setItem('newMessageNotification', JSON.stringify(notificationData));
}

document.addEventListener("DOMContentLoaded", async () => {
    const chatPage = document.getElementById("chat-page");
    const recipientId = chatPage.getAttribute("data-recipient-id");
    fetchCurrentUser().then(() => {
        if (recipientId) {
            console.log("Recipient ID:", recipientId);
            document.getElementById('messageForm').style = "display: block";
            selectedUserId = recipientId;
            fetchCurrentRecipient(selectedUserId);
            loadMessageHistory(selectedUserId);
            updateChatHeader();
            messageInput.focus();
        } else {
            fetchRecentUserChatWith();
        }
    }).catch((error) => {
        console.error("Error fetching current user:", error);
    });
});
messageForm.addEventListener('submit', sendMessage);

const chatMessages = document.getElementById('chat-messages');

chatMessages.addEventListener('scroll', async () => {
    if (chatMessages.scrollTop === 0 && !isFetchingMoreMessages) {
        const oldH = chatMessages.scrollHeight;
        isFetchingMoreMessages = true;
        await loadMessageHistory(selectedUserId, true);
        if (chatMessages.scrollHeight !== oldH) {
            chatMessages.scrollTop = chatMessages.scrollHeight - oldH;
            isFetchingMoreMessages = false;
        }
    }
})



function uploadImage(input) {
    var file = input.files[0];
    if (!file || !file.type.startsWith('image/')) return;
    const formData = new FormData();
    formData.append('image', file);
    fetch('/upload', {
        method: 'POST',
        body: formData,
        headers: {
            'X-CSRF-TOKEN': csrfToken
        }
    })
        .then(response => response.json())
        .then(response => {
            const imageContainer = document.getElementById('images-container');
            const container = document.createElement('div');
            container.className = 'message-preview-img-container';
            container.innerHTML = `<img src="/img/x-circle-regular-24.png" class="delete-preview-icon" onclick=deletePreview(this)>
                                    <img src=${response.imageUrl} class="message-preview-img">`
            imageContainer.appendChild(container);
        })
        .catch(error => console.log("Err: " + error))
}

function deletePreview(icon) {
    icon.closest('div').remove();
}

document.querySelector('.popup-image span').onclick = () => {
    document.querySelector('.popup-image').style.display = 'none';
}

function showPopupImage(img) {
    document.querySelector('.popup-image').style.display = 'block';
    document.querySelector('.popup-image img').src = img.src;
}