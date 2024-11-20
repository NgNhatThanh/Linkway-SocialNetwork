package com.social_network.dao;

import com.social_network.entity.Notification;
import com.social_network.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    int countByReadAndReceiver(boolean read, User receiver);

}
