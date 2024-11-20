package com.social_network.chatroom;

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
@Table(name = "chat_rooms") // Specify the table name
public class ChatRoom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-increment for MySQL
    private Long id; // Changed to Long for auto-generated IDs

    @Column(name = "chat_id", nullable = false) // Map this field to the column 'chat_id'
    private String chatId;

    @Column(name = "sender_id", nullable = false) // Map this field to the column 'sender_id'
    private String senderId;

    @Column(name = "recipient_id", nullable = false) // Map this field to the column 'recipient_id'
    private String recipientId;
}
