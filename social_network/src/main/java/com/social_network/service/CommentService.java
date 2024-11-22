package com.social_network.service;

import com.social_network.dao.CommentRepository;
import com.social_network.entity.Comment;
import com.social_network.entity.Notification;
import com.social_network.entity.Post;
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

    private NotificationService notificationService;

    public Page<Comment> findRootCommentsByPost(Post post, int page){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, COMMENT_PER_PAGE, sort);
        return commentRepository.findByPostAndParentCommentIsNull(pageable, post);
    }

    @Transactional
    public void saveComment(Comment comment){
        if(comment.getId() == 0){
            Notification notification = new Notification();
            notification.setSender(comment.getAuthor());
            if(comment.getParentComment() != null){
                notification.setReceiver(comment.getParentComment().getAuthor());
                notification.setContent(comment.getAuthor().getDisplayName() + " đã phản hồi bình luận của bạn.");
            }
            else{
                notification.setReceiver(comment.getPost().getAuthor());
                notification.setContent(comment.getAuthor().getDisplayName() + " đã bình luận ở bài viết của bạn.");
            }
            notification.setRedirectUrl("/post/" + comment.getPost().getId() + "#post-comments");
            notification.setCreatedAt(Date.from(Instant.now()));
            notificationService.sendNotification(notification);
        }

        commentRepository.save(comment);
    }

    public List<Comment> getChildComments(int parentId){
        return commentRepository.findByParentComment_Id(parentId);
    }

    public Comment findById(int id){
        return commentRepository.findById(id);
    }

}
