package com.social_network.chat;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ChatNotificationRepository extends JpaRepository<ChatNotification, Long> {
}
