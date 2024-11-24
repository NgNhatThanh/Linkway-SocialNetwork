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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "sender_id")
    private String senderId;

    @Column(name = "recipient_id")
    private String recipientId;

    @Column(name = "content")
    private String content;

    @Column(name = "read_status", nullable = false)
    private boolean read;
}
