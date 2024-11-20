package com.social_network.chat;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ChatNotificationService {

    @Autowired
    private ChatNotificationRepository chatNotificationRepository;

    public void sendNotification(String senderId, String recipientId, String content) {
        ChatNotification notification = ChatNotification.builder()
                .senderId(senderId)
                .recipientId(recipientId)
                .content(content)
                .build();
        chatNotificationRepository.save(notification);
    }

    public List<ChatNotification> findNotifications(String recipientId) {
        return chatNotificationRepository.findByRecipientId(recipientId);
    }

    public void deleteNotificationById(String recipientId) {
        chatNotificationRepository.deleteByRecipientId(recipientId);
    }

    public void markNotificationsAsRead(String recipientId) {
        List<ChatNotification> notifications = findUnreadNotificationsByRecipientId(recipientId);
        notifications.forEach(notification -> notification.setRead(true));
        chatNotificationRepository.saveAll(notifications);
    }

    public List<ChatNotification> findUnreadNotificationsByRecipientId(String recipientId) {
        return chatNotificationRepository.findByRecipientIdAndRead(recipientId, false);
    }

}
