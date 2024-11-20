package com.social_network.chatroom;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
import java.util.List;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByChatId(String chatId);

    List<ChatRoom> findBySenderId(String senderId);

    List<ChatRoom> findByRecipientId(String recipientId);

    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
