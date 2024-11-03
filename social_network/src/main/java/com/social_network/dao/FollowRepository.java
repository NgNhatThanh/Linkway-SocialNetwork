package com.social_network.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.social_network.entity.Follow;
import com.social_network.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findByFollower(User follower);

    List<Follow> findByFollowed(User followed);

    Optional<Follow> findByFollowerAndFollowed(User follower, User followed); // Changed to "followed"

    long countByFollowed(User user); // To count followers, now counts how many users follow this user

    long countByFollower(User user); // To count how many users this user is following

    void deleteByFollowerAndFollowed(User follower, User followed); // Use "followed" here as well
}
