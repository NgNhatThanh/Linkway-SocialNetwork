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

    Page<Follow> findByFollower(User follower, Pageable pageable);

    Page<Follow> findByFollowed(User followed, Pageable pageable);

    List<Follow> findByFollowed(User followed);

    Optional<Follow> findByFollowerAndFollowed(User follower, User followed);

    int countByFollowed(User user);

    int countByFollower(User user);

    void deleteByFollowerAndFollowed(User follower, User followed);
}
