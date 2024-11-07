package com.social_network.dao;

import com.social_network.entity.Post;
import com.social_network.entity.User;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    // @Query(nativeQuery = true, value = "SELECT \n" +
    // " p.*, \n" +
    // " COALESCE(t1.upvotes, 0) AS upvotes, \n" +
    // " COALESCE(t2.downvotes, 0) AS downvotes \n" +
    // "FROM \n" +
    // " posts p\n" +
    // "LEFT JOIN (\n" +
    // " SELECT post_id, COUNT(*) AS upvotes \n" +
    // " FROM post_votes \n" +
    // " WHERE vote_type = 1\n" +
    // " GROUP BY post_id\n" +
    // ") AS t1 ON p.id = t1.post_id\n" +
    // "LEFT JOIN (\n" +
    // " SELECT post_id, COUNT(*) AS downvotes \n" +
    // " FROM post_votes \n" +
    // " WHERE vote_type = -1\n" +
    // " GROUP BY post_id\n" +
    // ") AS t2 ON p.id = t2.post_id;\n")
    Page<Post> findAll(Pageable pageable);

    Page<Post> findByAuthor(User author, Pageable pageable);

    List<Post> findByAuthor(User author);

    Post findById(int id);

}
