package com.social_network.service;

import com.social_network.dao.NotificationRepository;
import com.social_network.entity.Notification;
import com.social_network.entity.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class NotificationService {

    private SimpMessagingTemplate messagingTemplate;

    private NotificationRepository notificationRepository;

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public int countUnreadNotifications(User receiver) {
        return notificationRepository.countByReadAndReceiver(false, receiver);
    }

    @Transactional
    public void sendNotification(Notification notification) {
        notificationRepository.save(notification);
        messagingTemplate.convertAndSendToUser(notification.getReceiver().getUsername(), "/notify",
                notification);
    }

    public List<Notification> getNotificationsOfReceiver(User receiver) {
        return notificationRepository.findByReceiver(receiver);
    }

    public Notification findById(int id){
        return notificationRepository.findById(id);
    }

}
