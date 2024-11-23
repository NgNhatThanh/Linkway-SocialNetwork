package com.social_network.chat;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {
    List<ChatMessage> findByChatId(String chatId, Pageable pageable);

    List<ChatMessage> findByIdLessThanAndChatIdEquals(Long lastId, String chatId, Pageable pageable);
}
