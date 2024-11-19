package com.social_network.dao;

import com.social_network.entity.Post;
import com.social_network.entity.Tag;
import com.social_network.entity.User;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface PostRepository extends JpaRepository<Post, Integer> {

    Page<Post> findAll(Pageable pageable);

    Page<Post> findByAuthor(User author, Pageable pageable);

    List<Post> findByAuthor(User author);

    Post findById(int id);

    Page<Post> findPostByTags(List<Tag> tags, Pageable pageable);

    int countByAuthor(User author);

    Page<Post> findPostsByTitleContainingIgnoreCaseAndCreatedAtBetween(String query,
                                                                       Date from,
                                                                       Date to,
                                                                       Pageable pageable);

    Page<Post> findPostsByTitleContainingIgnoreCaseAndCreatedAtBetweenAndTagsIn(String query,
                                                                Date dateFrom,
                                                                Date dateTo,
                                                                List<Tag> tags,
                                                                Pageable pageable);

}
