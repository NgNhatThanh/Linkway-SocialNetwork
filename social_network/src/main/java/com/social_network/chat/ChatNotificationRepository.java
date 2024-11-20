package com.social_network.chat;

import java.util.List;

import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface ChatNotificationRepository extends JpaRepository<ChatNotification, Long> {
    void deleteBySenderIdAndRecipientId(String senderId, String recipientId);

    List<ChatNotification> findByRecipientId(String recipientId);

    @Transactional
    @Query("DELETE FROM ChatNotification cn WHERE cn.recipientId = :recipientId AND cn.id > 0")
    void deleteByRecipientId(@Param("recipientId") String recipientId);

    List<ChatNotification> findByRecipientIdAndRead(String recipientId, boolean b);

}
