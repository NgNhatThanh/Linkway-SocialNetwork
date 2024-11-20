package com.social_network.service;

import com.social_network.dao.NotificationRepository;
import com.social_network.entity.Notification;
import com.social_network.entity.User;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class NotificationService {

    private NotificationRepository notificationRepository;

    public Notification save(Notification notification) {
        return notificationRepository.save(notification);
    }

    public int countUnreadNotifications(User receiver) {
        return notificationRepository.countByReadAndReceiver(false, receiver);
    }

}
