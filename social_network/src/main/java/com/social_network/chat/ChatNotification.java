package com.social_network.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.persistence.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "chat_notifications")
public class ChatNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // ID sẽ được tự động sinh ra trong MySQL
    private Long id; // Thay String thành Long nếu ID tự sinh trong cơ sở dữ liệu

    @Column(name = "sender_id")
    private String senderId;

    @Column(name = "recipient_id")
    private String recipientId;

    @Column(name = "content")
    private String content;

    @Column(name = "read_status", nullable = false)
    private boolean read; // New column to track the read/unread status
}
