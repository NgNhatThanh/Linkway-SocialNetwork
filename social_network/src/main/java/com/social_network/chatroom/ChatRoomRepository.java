package com.social_network.chatroom;

import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
    Optional<ChatRoom> findByChatId(String chatId);

    Optional<ChatRoom> findBySenderIdAndRecipientId(String senderId, String recipientId);
}
