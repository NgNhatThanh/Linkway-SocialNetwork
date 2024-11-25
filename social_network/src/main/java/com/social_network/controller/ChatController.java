package com.social_network.controller;

import com.social_network.entity.ChatMessage;
import com.social_network.service.ChatMessageService;
import com.social_network.entity.ChatNotification;
import com.social_network.service.ChatNotificationService;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ChatController {
        private final SimpMessagingTemplate messagingTemplate;
        private final ChatMessageService chatMessageService;
        private final ChatNotificationService chatNotificationService;

        @MessageMapping("/chat.sendMessage")
        public void processMessage(@Payload ChatMessage chatMessage) {
                chatMessage.setSentAt(Date.from(Instant.now()));

                chatNotificationService.sendNotification(
                                chatMessage.getSenderId(),
                                chatMessage.getRecipientId(),
                                chatMessage.getContent());

                messagingTemplate.convertAndSendToUser(
                                chatMessage.getRecipientId(), "/queue/messages",
                                chatMessage);

        }

        @GetMapping("/message/notifications/{recipientId}")
        public ResponseEntity<List<ChatNotification>> findUnreadNotifications(@PathVariable String recipientId) {
                try {
                        List<ChatNotification> notifications = chatNotificationService
                                        .findUnreadNotificationsByRecipientId(recipientId);
                        return ResponseEntity.ok(notifications);
                } catch (Exception e) {
                        return ResponseEntity.status(500).body(new ArrayList<>());
                }
        }

        @GetMapping("/message/notifications/{senderId}/sender")
        public ResponseEntity<List<ChatNotification>> findUnreadNotificationsWithSender(@PathVariable String senderId) {
                try {
                        List<ChatNotification> notifications = chatNotificationService
                                        .findUnreadNotificationsBySenderId(senderId);
                        return ResponseEntity.ok(notifications);
                } catch (Exception e) {
                        return ResponseEntity.status(500).body(new ArrayList<>());
                }
        }

        @GetMapping("/messages/{senderId}/{recipientId}")
        public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable String senderId,
                        @PathVariable String recipientId,
                        @RequestParam(value = "lastMessageId", defaultValue = "-1") long lastMessageId) {
                List<ChatMessage> messages;
                if (lastMessageId == -1)
                        messages = chatMessageService.findChatMessages(senderId, recipientId);
                else
                        messages = chatMessageService.findChatMessagesBeforeId(senderId, recipientId, lastMessageId);
                return ResponseEntity.ok(messages);
        }

        @GetMapping("/chat/{recipientId}")
        public String openChatWithUser(@PathVariable String recipientId, Model model) {
                try {
                        model.addAttribute("recipientId", recipientId);
                        return "chatpage";
                } catch (Exception e) {
                        model.addAttribute("error", "Không thể mở giao diện chat.");
                        return "error";
                }
        }

        @GetMapping("/chat")
        public String showChatPage() {
                return "chatpage";
        }

        @DeleteMapping("/message/notifications/{recipientId}/delete")
        public ResponseEntity<?> deleteAllNotifications(@PathVariable String recipientId) {
                try {
                        chatNotificationService.deleteNotificationById(recipientId);
                        return ResponseEntity.ok().build();
                } catch (Exception e) {
                        return ResponseEntity.status(500).body("Error while deleting notifications: " + e.getMessage());
                }
        }

        @PutMapping("/message/notifications/{recipientId}/mark-as-read")
        public ResponseEntity<String> markNotificationsAsRead(@PathVariable String recipientId) {
                try {
                        chatNotificationService.markNotificationsAsRead(recipientId);
                        return ResponseEntity.ok("Notifications marked as read successfully");
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body("Error while marking notifications as read: " + e.getMessage());
                }
        }

        @PutMapping("/message/notifications/{senderId}/{recipientId}/mark-as-read")
        public ResponseEntity<String> markNotificationsAsReadWithSenderAndRecipient(@PathVariable String senderId,
                        @PathVariable String recipientId) {
                try {
                        chatNotificationService.markNotificationsAsReadWithSenderAndRecipient(senderId, recipientId);
                        return ResponseEntity.ok("Notifications marked as read successfully");
                } catch (Exception e) {
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body("Error while marking notifications as read: " + e.getMessage());
                }
        }

}
