package com.social_network.chatroom;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

        private final ChatRoomRepository chatRoomRepository;

        public Optional<String> getChatRoomId(
                        String senderId,
                        String recipientId,
                        boolean createNewRoomIfNotExists) {
                Optional<String> existingChatId = chatRoomRepository
                                .findBySenderIdAndRecipientId(senderId, recipientId)
                                .map(ChatRoom::getChatId);
                return existingChatId.isPresent() ? existingChatId
                                : createNewRoomIfNotExists ? Optional.of(createChatId(senderId, recipientId))
                                                : Optional.empty();
        }

        private String createChatId(String senderId, String recipientId) {
                var chatId = String.format("%s_%s", senderId, recipientId);
                ChatRoom senderRecipient = ChatRoom
                                .builder()
                                .chatId(chatId)
                                .senderId(senderId)
                                .recipientId(recipientId)
                                .build();

                ChatRoom recipientSender = ChatRoom
                                .builder()
                                .chatId(chatId)
                                .senderId(recipientId)
                                .recipientId(senderId)
                                .build();

                chatRoomRepository.save(senderRecipient);
                chatRoomRepository.save(recipientSender);
                return chatId;
        }

        public List<ChatRoom> getChatRoomsBySenderId(String senderId) {
                return chatRoomRepository.findBySenderId(senderId);
        }
}
