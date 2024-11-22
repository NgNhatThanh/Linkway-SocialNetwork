let stompClient = null;
// Listen for storage events from main.js
const currentId = document.getElementById('current-id').value;
const currentUsername = document.getElementById('current-username').value;
var unreadNotifications;
const csrfToken = document.getElementById('csrf-token').value;
const maxNotificationsFetch = 5;
var lastId = 9999999;
const emptyItem = document.createElement('li');
emptyItem.innerHTML = "<p> Không có thông báo nào. </p>";

const notificationsList = document.getElementById('notifications-list');

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
}

function onConnected() {
    stompClient.subscribe(`/user/${currentUsername}/queue/messages`, onMessageReceived);
    stompClient.subscribe(`/user/${currentUsername}/notify`, onNotificationReceived)
    stompClient.send("/app/user.updateStatus", {}, JSON.stringify({ currentUsername, status: 'ONLINE' }));
}

function onError(error) {
    console.error('WebSocket error:', error);
    showError('Could not connect to WebSocket server. Please refresh the page and try again.');
    connectingElement.classList.add('hidden');
}

function showFlowNotification(message, type = 'info', timeout = 5000) {
    const notification = document.createElement('div');
    notification.classList.add('notification', type);
    notification.textContent = message;
    const notificationContainer = document.getElementById('notification-container');
    notificationContainer.appendChild(notification);
    setTimeout(() => {
        notification.remove();
    }, timeout);
}

function onMessageReceived(message) {
    const messageData = JSON.parse(message.body);
    showFlowNotification(`New message from ${messageData.senderId}`, 'message');
    increaseUnreadMessageCount();
}


// Update notification icon on the navbar with the count of unread notifications
function increaseUnreadMessageCount() {
    const unreadMessageCount = document.getElementById('unread-messages-count');
    if (unreadMessageCount) {
        var count = unreadMessageCount.textContent === '' ? '1' : parseInt(unreadMessageCount.textContent) + 1;
        if (count > 0) {
            unreadMessageCount.textContent = count.toString();
        } else {
            unreadMessageCount.textContent = '';
        }
    }
}

function toggleNotiListVisibility(){
    document.getElementById('notifications-dropdown').classList.toggle('hidden');
}

function onNotificationReceived(notification){
    const unreadCount = document.getElementById('unread-notification-count');
    unreadCount.textContent = unreadCount.textContent === '' ? '1' : parseInt(unreadCount.textContent) + 1;
    const noti = JSON.parse(notification.body);
    if(notificationsList.contains(emptyItem)) notificationsList.removeChild(emptyItem);
    addNotiToList(noti, true);
    showFlowNotification(noti.content, 'info', 5000);
}

function addNotiToList(noti, prepend = false){
    noti.createdAt = formatDate(new Date(noti.createdAt));
    const item = document.createElement('li');
    item.style = "display: flex";
    item.id = `notification-item-${noti.id}`;
    if(noti.read) item.classList.add('read');
    const notiCard = `    <a style="display: flex" href=${noti.redirectUrl + '?ref=notif&notifId=' + noti.id} class="noti-redirect">
                                    <img src=${noti.sender.avatarImagePath} class="notifcation-thumbnail">
                                    <div>
                                        <p>${noti.content}</p>
                                        <p>${noti.createdAt}</p>
                                    </div>
                                </a>
                                <div>
                                    <button title="Đánh dấu là đã đọc" onclick=markNotificationAsRead(${noti.id})>
                                        <i class='bx bx-check-circle'></i>
                                    </button>\
                                    <button title="Xóa thông báo" onclick=deleteNotification(${noti.id})>
                                        <i class='bx bx-trash'></i>
                                    </button>\
                                </div>`;
    item.innerHTML = notiCard;
    if(prepend) notificationsList.prepend(item);
    else notificationsList.appendChild(item);
}


function formatDate(date){
    const year = date.getFullYear();
    const month = String(date.getMonth() + 1).padStart(2, '0'); // Months are 0-based
    const day = String(date.getDate()).padStart(2, '0');
    const hours = String(date.getHours() % 12 || 12).padStart(2, '0'); // For 12-hour format
    const minutes = String(date.getMinutes()).padStart(2, '0');
    return `${year}/${month}/${day} ${hours}:${minutes}`;
}

function getUnreadNotifications(){
    fetch(`/notifications/${currentId}/unread`)
        .then(count => count.json())
        .then(res => {
            unreadNotifications = res;
            if(res && res > 0) document.getElementById('unread-notification-count').textContent = unreadNotifications;
        })
}


function fetchNotifications(){
    fetch(`/notifications/${currentId}/${lastId}/${maxNotificationsFetch}`)
        .then(res => res.json())
        .then(notiList => {
            notificationsList.removeChild(notificationsList.lastChild);
            notiList.forEach(noti =>{
                lastId = noti.id;
                addNotiToList(noti);
            })

            if(notiList.length === maxNotificationsFetch){
                const item = document.createElement('li');
                item.innerHTML = `<button onclick=fetchNotifications()>
                                    Hiển thị thêm
                                </button>`;
                notificationsList.appendChild(item);
            }

            if(notificationsList.children.length === 0) notificationsList.appendChild(emptyItem);
        });
}

function markNotificationAsRead(notiId){
    fetch(`/notifications/${notiId}/markAsRead`,{
        method: 'POST',
        headers:{
            'X-CSRF-TOKEN': csrfToken
        }
    })
        .then(() => {
            const item = document.getElementById(`notification-item-${notiId}`);
            item.classList.add('read');
            const unreadCount = document.getElementById('unread-notification-count');
            unreadCount.textContent = unreadCount.textContent === '1' ? '' : parseInt(unreadCount.textContent) - 1;
        })
}

function deleteNotification(notiId){
    fetch(`/notifications/${notiId}/delete`,{
        method: 'DELETE',
        headers:{
            'X-CSRF-TOKEN': csrfToken
        }
    })
        .then(() => {
            const item = document.getElementById(`notification-item-${notiId}`);
            item.remove();
            const unreadCount = document.getElementById('unread-notification-count');
            unreadCount.textContent = unreadCount.textContent === '1' ? '' : parseInt(unreadCount.textContent) - 1;
            if(notificationsList.children.length === 0){
                notificationsList.appendChild(emptyItem);
            }
        })
}

function getUnreadMessagesCount() {
    fetch(`/message/notifications/${currentUsername}`)
        .then(response => response.json())
        .then(notifications => {
            if(notifications.length > 0) document.getElementById('unread-messages-count').textContent = notifications.length;
        })
        .catch(error => console.log(error));
}

if(currentUsername){
    connect();
    getUnreadNotifications();
    fetchNotifications();
    getUnreadMessagesCount();

    const avatarIcon = document.getElementById("avatar-icon");
    const dropdownMenu = document.getElementById("dropdown-menu");

    const notiButton = document.getElementById('notification-button');
    const notiDropdown = document.getElementById('notifications-dropdown');
    avatarIcon.addEventListener("click", function () {
        dropdownMenu.style.display = dropdownMenu.style.display === "block" ? "none" : "block";
    });
    document.addEventListener("click", function (event) {
        if (!avatarIcon.contains(event.target) && !dropdownMenu.contains(event.target)) {
            dropdownMenu.style.display = "none";
        }
        if(!notiDropdown.contains(event.target) && !notiButton.contains(event.target)){
            document.getElementById('notifications-dropdown').classList.add('hidden');
        }
    });
}
