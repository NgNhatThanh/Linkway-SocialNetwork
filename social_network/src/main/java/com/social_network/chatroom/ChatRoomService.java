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
                // Try to find an existing chat room between sender and recipient
                Optional<String> existingChatId = chatRoomRepository
                                .findBySenderIdAndRecipientId(senderId, recipientId)
                                .map(ChatRoom::getChatId); // If found, return the chatId

                // If chat room exists, return it, else create a new one if flag is true
                return existingChatId.isPresent() ? existingChatId
                                : createNewRoomIfNotExists ? Optional.of(createChatId(senderId, recipientId))
                                                : Optional.empty();
        }

        private String createChatId(String senderId, String recipientId) {
                var chatId = String.format("%s_%s", senderId, recipientId);

                // Create two chat room entries (one for sender-recipient and one for
                // recipient-sender)
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

                // Save both chat rooms to the repository
                chatRoomRepository.save(senderRecipient);
                chatRoomRepository.save(recipientSender);

                // Return the generated chat ID
                return chatId;
        }

        public List<ChatRoom> getChatRoomsBySenderId(String senderId) {
                return chatRoomRepository.findBySenderId(senderId);
        }
}
