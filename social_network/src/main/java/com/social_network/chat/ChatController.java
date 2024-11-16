package com.social_network.chat;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.social_network.entity.User;
import com.social_network.service.UserService;

import java.util.List;

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

                // Send a notification to the recipient's queue
                messagingTemplate.convertAndSendToUser(
                                chatMessage.getRecipientId(), "/queue/messages",
                                new ChatNotification(
                                                savedMsg.getId(),
                                                savedMsg.getSenderId(),
                                                savedMsg.getRecipientId(),
                                                savedMsg.getContent()));
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

        @PostMapping("/api/chat/sendNotification")
        public ResponseEntity<String> sendNotification(@RequestBody ChatNotification chatNotification) {
                try {
                        // Save the notification to the database
                        chatNotificationService.sendNotification(
                                        chatNotification.getSenderId(),
                                        chatNotification.getRecipientId(),
                                        chatNotification.getContent());

                        // Send the notification to the recipient's queue via WebSocket
                        messagingTemplate.convertAndSendToUser(
                                        chatNotification.getRecipientId(), "/queue/messages", chatNotification);

                        return ResponseEntity.ok("Notification sent successfully.");
                } catch (Exception e) {
                        return ResponseEntity.status(500).body("Failed to send notification: " + e.getMessage());
                }
        }
}
