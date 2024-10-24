package com.social_network.dao;

import com.social_network.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

    User findById(int id);

    boolean existsByEmail(String emmail);

    boolean existsByUsername(String username);
}
