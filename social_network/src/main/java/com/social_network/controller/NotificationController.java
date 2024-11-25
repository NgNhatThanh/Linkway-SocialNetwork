package com.social_network.controller;

import com.social_network.entity.Notification;
import com.social_network.service.NotificationService;
import com.social_network.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@AllArgsConstructor
public class NotificationController {

    private final UserService userService;

    private NotificationService notificationService;

    @GetMapping("/notifications/{userId}/unread")
    public ResponseEntity<Integer> getUnreadNotifications(@PathVariable int userId) {
        return ResponseEntity.ok(notificationService.countUnreadNotifications(
                userService.findById(userId)
        ));
    }

    @GetMapping("/notifications/{userId}/{lastId}/{limit}")
    @ResponseBody
    public List<Notification> getNotifications(@PathVariable int userId,
                                               @PathVariable int lastId,
                                               @PathVariable int limit) {
        return notificationService.getNotificationsOfReceiver(
                userService.findById(userId),
                lastId,
                limit
        );
    }

    @PostMapping("/notifications/{notiId}/markAsRead")
    @ResponseBody
    public void markAsRead(@PathVariable int notiId) {
        Notification noti = notificationService.findById(notiId);
        noti.setRead(true);
        notificationService.save(noti);
    }

    @DeleteMapping("/notifications/{notiId}/delete")
    @ResponseBody
    public void deleteNotification(@PathVariable int notiId) {
        Notification noti = notificationService.findById(notiId);
        notificationService.deleteNotification(noti);
    }

}
