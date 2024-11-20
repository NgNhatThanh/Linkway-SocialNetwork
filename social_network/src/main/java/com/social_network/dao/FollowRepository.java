package com.social_network.dao;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.social_network.entity.Follow;
import com.social_network.entity.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface FollowRepository extends JpaRepository<Follow, Integer> {
    List<Follow> findByFollower(User follower);

    Page<Follow> findByFollower(User follower, Pageable pageable); // Added this line

    Page<Follow> findByFollowed(User followed, Pageable pageable); // Added this line

    List<Follow> findByFollowed(User followed);

    Optional<Follow> findByFollowerAndFollowed(User follower, User followed); // Changed to "followed"

    int countByFollowed(User user); // To count followers, now counts how many users follow this user

    int countByFollower(User user); // To count how many users this user is following

    void deleteByFollowerAndFollowed(User follower, User followed); // Use "followed" here as well
}
