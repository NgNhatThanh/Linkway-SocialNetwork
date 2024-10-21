package com.social_network.entity.follow;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "follows")
@IdClass(FollowId.class)
public class Follow {

    @Id
    @Column(name = "followed_id")
    private String followedId;

    @Id
    @Column(name = "following_id")
    private String followingId;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
