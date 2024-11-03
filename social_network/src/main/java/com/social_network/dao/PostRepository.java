package com.social_network.dao;

import com.social_network.entity.Post;
import com.social_network.entity.User;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    List<Post> findByAuthor(User author);

    Post findById(int id);

}
