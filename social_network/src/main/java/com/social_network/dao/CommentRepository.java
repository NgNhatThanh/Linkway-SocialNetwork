package com.social_network.dao;

import com.social_network.entity.Comment;
import com.social_network.entity.Post;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment, Integer> {

    Page<Comment> findByPostAndParentCommentIsNull(Pageable pageable, Post post);

    List<Comment> findByParentComment_Id(int parentId);

}
