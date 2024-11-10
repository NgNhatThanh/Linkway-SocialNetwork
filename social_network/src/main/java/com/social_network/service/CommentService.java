package com.social_network.service;

import com.social_network.dao.CommentRepository;
import com.social_network.entity.Comment;
import com.social_network.entity.Post;
import jakarta.persistence.Transient;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class CommentService {

    private final int COMMENT_PER_PAGE = 10;

    private CommentRepository commentRepository;

    public Page<Comment> findRootCommentsByPost(Post post, int page){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, COMMENT_PER_PAGE, sort);
        return commentRepository.findByPostAndParentCommentIsNull(pageable, post);
    }

    @Transactional
    public void addComment(Comment comment){
        comment.setCreatedAt(Date.from(Instant.now()));
        commentRepository.save(comment);
    }

    public List<Comment> getChildComments(int parentId){
        return commentRepository.findByParentComment_Id(parentId);
    }

}
