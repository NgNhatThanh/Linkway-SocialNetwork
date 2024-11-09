package com.social_network.service;

import com.social_network.dao.CommentRepository;
import com.social_network.entity.Comment;
import com.social_network.entity.Post;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CommentService {

    private final int COMMENT_PER_PAGE = 10;

    private CommentRepository commentRepository;

    public Page<Comment> findRootCommentsByPost(Post post, int page){
        Pageable pageable = PageRequest.of(page - 1, COMMENT_PER_PAGE);
        return commentRepository.findByPostAndParentCommentIsNull(pageable, post);
    }

}
