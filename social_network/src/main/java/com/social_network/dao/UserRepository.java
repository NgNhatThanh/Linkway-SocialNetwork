package com.social_network.dao;

import com.social_network.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByUsername(String username);

    User findById(int id);

    User findByEmail(String email);

    boolean existsByEmail(String emmail);

    boolean existsByUsername(String username);

    Page<User> findAll(Pageable pageable);

    List<User> findAll();

    Optional<User> findByDisplayName(String displayName);

    @Query(nativeQuery = true,
    value = "select * from users " +
            "where lower(username) like lower(concat(?1, '%')) " +
            "or lower(display_name) like lower(concat(?1, '%'))")
    Page<User> findByKeyword(String keyword, Pageable pageable);

}
