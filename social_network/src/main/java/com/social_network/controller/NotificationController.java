package com.social_network.controller;

import com.social_network.entity.Notification;
import com.social_network.entity.User;
import com.social_network.service.NotificationService;
import com.social_network.service.UserService;
import jakarta.persistence.Column;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.converter.SimpleMessageConverter;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
@AllArgsConstructor
public class NotificationController {

    private final UserService userService;

    private SimpMessagingTemplate messagingTemplate;

    private NotificationService notificationService;

    @GetMapping("/notifications/{userId}/unread")
    public ResponseEntity<Integer> getUnreadNotifications(@PathVariable int userId,
            HttpServletRequest request) {
        return ResponseEntity.ok(notificationService.countUnreadNotifications(
                userService.findById(userId)
        ));
    }

    @GetMapping("/notifications/{userId}")
    @ResponseBody
    public List<Notification> getNotifications(@PathVariable int userId) {
        return notificationService.getNotificationsOfReceiver(
                userService.findById(userId)
        );
    }

}
