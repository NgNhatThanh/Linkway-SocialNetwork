package com.social_network.chat;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.social_network.entity.User;
import com.social_network.service.UserService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Controller
@RequiredArgsConstructor
public class ChatController {

        private final SimpMessagingTemplate messagingTemplate;
        private final ChatMessageService chatMessageService;
        private final ChatNotificationService chatNotificationService;
        private final UserService userService;

        // Receive the chat message and send to the recipient
        @MessageMapping("/chat.sendMessage")
        public void processMessage(@Payload ChatMessage chatMessage) {
                // Save message to the database (or memory)
                ChatMessage savedMsg = chatMessageService.save(chatMessage);

                chatNotificationService.sendNotification(
                                chatMessage.getSenderId(),
                                chatMessage.getRecipientId(),
                                chatMessage.getContent());

                // Send a notification to the recipient's queue
                messagingTemplate.convertAndSendToUser(
                                chatMessage.getRecipientId(), "/queue/messages",
                                new ChatNotification(
                                                savedMsg.getId(),
                                                savedMsg.getSenderId(),
                                                savedMsg.getRecipientId(),
                                                savedMsg.getContent(), false));

        }

        @GetMapping("/notifications/{recipientId}")
        public ResponseEntity<List<ChatNotification>> findUnreadNotifications(@PathVariable String recipientId) {
                try {
                        // Fetch unread notifications using the service
                        List<ChatNotification> notifications = chatNotificationService
                                        .findUnreadNotificationsByRecipientId(recipientId);
                        return ResponseEntity.ok(notifications);
                } catch (Exception e) {
                        // Handle error if fetching notifications fails
                        return ResponseEntity.status(500).body(new ArrayList<>());
                }
        }

        @GetMapping("/notifications/{senderId}/sender")
        public ResponseEntity<List<ChatNotification>> findUnreadNotificationsWithSender(@PathVariable String senderId) {
                try {
                        // Fetch read notifications using the service
                        List<ChatNotification> notifications = chatNotificationService
                                        .findUnreadNotificationsBySenderId(senderId);
                        return ResponseEntity.ok(notifications);
                } catch (Exception e) {
                        // Handle error if fetching notifications fails
                        return ResponseEntity.status(500).body(new ArrayList<>());
                }
        }

        // Get the chat messages between two users
        @GetMapping("/messages/{senderId}/{recipientId}")
        public ResponseEntity<List<ChatMessage>> findChatMessages(@PathVariable String senderId,
                        @PathVariable String recipientId) {
                // Retrieve chat messages from the database
                List<ChatMessage> messages = chatMessageService.findChatMessages(senderId, recipientId);
                return ResponseEntity.ok(messages);
        }

        @GetMapping("/chat/{recipientId}")
        public String openChatWithUser(@PathVariable String recipientId, Model model) {
                try {
                        // Thêm recipientId vào model để frontend có thể sử dụng
                        model.addAttribute("recipientId", recipientId);
                        return "index"; // Trả về giao diện chat giữa hai người
                } catch (Exception e) {
                        model.addAttribute("error", "Không thể mở giao diện chat.");
                        return "error"; // Trả về trang lỗi nếu không thể mở giao diện chat
                }
        }

        // Show the chat page (Frontend will display this page)
        @GetMapping("/chat")
        public String showChatPage() {
                return "index"; // Return the view for chat UI
        }

        @DeleteMapping("/notifications/{recipientId}/delete")
        public ResponseEntity<?> deleteAllNotifications(@PathVariable String recipientId) {
                try {
                        // Xóa tất cả thông báo của người dùng
                        chatNotificationService.deleteNotificationById(recipientId);
                        return ResponseEntity.ok().build(); // Trả về 200 OK nếu xóa thành công
                } catch (Exception e) {
                        // Xử lý lỗi nếu không xóa được
                        return ResponseEntity.status(500).body("Error while deleting notifications: " + e.getMessage());
                }
        }

        @PutMapping("/notifications/{recipientId}/mark-as-read")
        public ResponseEntity<String> markNotificationsAsRead(@PathVariable String recipientId) {
                try {
                        // Đánh dấu tất cả thông báo là đã đọc
                        chatNotificationService.markNotificationsAsRead(recipientId);
                        return ResponseEntity.ok("Notifications marked as read successfully");
                } catch (Exception e) {
                        // Xử lý lỗi nếu không thể đánh dấu thông báo
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body("Error while marking notifications as read: " + e.getMessage());
                }
        }

        @PutMapping("/notifications/{senderId}/{recipientId}/mark-as-read")
        public ResponseEntity<String> markNotificationsAsReadWithSenderAndRecipient(@PathVariable String senderId,
                        @PathVariable String recipientId) {
                try {
                        // Đánh dấu tất cả thông báo là đã đọc
                        chatNotificationService.markNotificationsAsReadWithSenderAndRecipient(senderId, recipientId);
                        return ResponseEntity.ok("Notifications marked as read successfully");
                } catch (Exception e) {
                        // Xử lý lỗi nếu không thể đánh dấu thông báo
                        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                        .body("Error while marking notifications as read: " + e.getMessage());
                }
        }

}
