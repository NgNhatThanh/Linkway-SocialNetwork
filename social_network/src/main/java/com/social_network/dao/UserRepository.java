package com.social_network.dao;

import com.social_network.entity.Status;
import com.social_network.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    Optional<User> findById(int id);

    User findByEmail(String email);

    boolean existsByEmail(String emmail);

    boolean existsByUsername(String username);

    Page<User> findAll(Pageable pageable);

    List<User> findAll();

    Optional<User> findByDisplayName(String displayName);

    List<User> findAllByStatus(Status status);

}
