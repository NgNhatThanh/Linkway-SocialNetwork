package com.social_network.chat;

import com.social_network.chatroom.ChatRoomService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatMessageService {
    private final ChatMessageRepository repository;
    private final ChatRoomService chatRoomService;

    private final int MESSAGE_PER_FETCH = 20;

    public ChatMessage save(ChatMessage chatMessage) {
        var chatId = chatRoomService
                .getChatRoomId(chatMessage.getSenderId(), chatMessage.getRecipientId(), true)
                .orElseThrow(() -> new RuntimeException("Chat room not found")); // Throw a specific exception
        chatMessage.setChatId(chatId);
        repository.save(chatMessage);
        return chatMessage;
    }

    public List<ChatMessage> findChatMessages(String senderId, String recipientId) {
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        System.out.println("ABCEEF: " + chatId);
        if(chatId.isEmpty()) return new ArrayList<>();
        Pageable pageable = PageRequest.of(0, MESSAGE_PER_FETCH, Sort.by(Sort.Direction.DESC, "id"));
        return repository.findByChatId(chatId.get(), pageable);
    }

    public List<ChatMessage> findChatMessagesBeforeId(String senderId, String recipientId, long lastId) {
        var chatId = chatRoomService.getChatRoomId(senderId, recipientId, false);
        Pageable pageable = PageRequest.of(0, MESSAGE_PER_FETCH, Sort.by(Sort.Direction.DESC, "id"));
        return repository.findByIdLessThanAndChatIdEquals(lastId, chatId.get(), pageable);
    }

}
