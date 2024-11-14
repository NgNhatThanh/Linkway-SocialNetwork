package com.social_network.chat;

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

}
