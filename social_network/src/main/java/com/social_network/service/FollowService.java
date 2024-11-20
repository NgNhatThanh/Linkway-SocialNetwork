package com.social_network.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.social_network.dao.FollowRepository;
import com.social_network.entity.Follow;
import com.social_network.entity.User;
import com.social_network.exception.FollowException;

import jakarta.transaction.Transactional;

import java.time.Instant;
import java.util.List;

@Service
public class FollowService {

    private final FollowRepository followRepository;
    private final UserService userService;

    public FollowService(FollowRepository followRepository, UserService userService) {
        this.followRepository = followRepository;
        this.userService = userService;
    }

    public boolean isFollowing(String followerUsername, String followedUsername) {
        User follower = userService.findByUsername(followerUsername).orElse(null);
        User followed = userService.findByUsername(followedUsername).orElse(null);
        if (follower == null || followed == null) {
            return false;
        }

        return followRepository.findByFollowerAndFollowed(follower, followed).isPresent();
    }

    public long getFollowingCount(User user) {
        return followRepository.countByFollower(user);
    }

    public int getFollowerCount(User user) {
        return followRepository.countByFollowed(user); // Updated to count followers
    }

    public void followUser(User follower, User followed) {
        if (follower.getId() == followed.getId()) {
            throw new FollowException("Cannot follow yourself.");
        }

        Follow follow = new Follow();
        follow.setFollower(follower);
        follow.setFollowed(followed);
        follow.setCreatedAt(Instant.now());
        followRepository.save(follow);
    }

    @Transactional
    public void followUser(String followerUsername, String followedUsername) {
        User follower = userService.findByUsername(followerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Follower user not found"));
        User followed = userService.findByUsername(followedUsername)
                .orElseThrow(() -> new IllegalArgumentException("Followed user not found"));

        if (!isFollowing(followerUsername, followedUsername)) {
            Follow follow = new Follow();
            follow.setFollower(follower);
            follow.setFollowed(followed); // Updated to use 'followed'
            follow.setCreatedAt(Instant.now());
            followRepository.save(follow);
        }
    }

    @Transactional
    public void unfollow(String followerUsername, String followedUsername) {
        User follower = userService.findByUsername(followerUsername)
                .orElseThrow(() -> new IllegalArgumentException("Follower user not found"));
        User followed = userService.findByUsername(followedUsername)
                .orElseThrow(() -> new IllegalArgumentException("Followed user not found"));

        followRepository.deleteByFollowerAndFollowed(follower, followed); // Updated to use 'followed'
    }

    public void unfollowUser(User follower, User followed) {
        if (follower.getId() == followed.getId()) {
            throw new FollowException("Cannot unfollow yourself.");
        }
        followRepository.deleteByFollowerAndFollowed(follower, followed); // Updated to use 'followed'
    }

    public List<User> getFollowers(User user) {
        List<Follow> follows = followRepository.findByFollowed(user);
        return follows.stream()
                .map(Follow::getFollower)
                .toList();
    }

    public Page<Follow> getFollowers(User user, int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        return followRepository.findByFollowed(user, pageable);
    }

    public Page<Follow> getFollowing(User user, int page) {
        Pageable pageable = PageRequest.of(page - 1, 10);
        return followRepository.findByFollower(user, pageable);
    }


    public List<User> getFollowing(User user) {
        List<Follow> follows = followRepository.findByFollower(user);
        return follows.stream()
                .map(Follow::getFollowed)
                .toList();
    }
}
