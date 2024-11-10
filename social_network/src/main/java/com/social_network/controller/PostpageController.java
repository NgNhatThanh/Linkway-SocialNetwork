package com.social_network.controller;

import com.social_network.dto.request.CommentDTO;
import com.social_network.entity.Comment;
import com.social_network.entity.Post;
import com.social_network.entity.User;
import com.social_network.service.*;
import com.social_network.util.MarkdownRenderUtil;
import com.social_network.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@AllArgsConstructor
public class PostpageController {

    private UserService userService;

    private SecurityUtil securityUtil;

    private PostService postService;

    private MarkdownRenderUtil markdownRenderUtil;

    private FollowService followService;

    private VoteService voteService;

    private CommentService commentService;

    @GetMapping("/post/{postId}")
    public String showPostPage(@PathVariable("postId") int postId,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               Model model,
                               HttpServletRequest request){
        postService.increaseView(postId);
        Post post = postService.findById(postId);
        String htmlContent = markdownRenderUtil.convertToHtml(post.getContent());
        String currentUsername = securityUtil.getCurrentUser().getUsername();

        Page<Comment> rootComments = commentService.findRootCommentsByPost(post, page);

        int voteType = voteService.getUserPostVoteType(postId, (int)request.getSession().getAttribute("id"));
        long authorFollowers = followService.getFollowerCount(post.getAuthor());
        model.addAttribute("post", post);
        model.addAttribute("postContent", htmlContent);
        model.addAttribute("authorFollowers", authorFollowers);
        model.addAttribute("isCurrentUser",
                post.getAuthor().getUsername().equals(currentUsername));
        model.addAttribute("isFollowing",
                followService.isFollowing(currentUsername,
                        post.getAuthor().getUsername()));
        model.addAttribute("upvoted", voteType == 1);
        model.addAttribute("downvoted", voteType == -1);
        model.addAttribute("userComment", new CommentDTO());
        model.addAttribute("rootComments" ,rootComments);
        return "home/postpage";
    }

    @PostMapping("/post/{postId}/{voteType}")
    public String votePost(@PathVariable("postId") int postId,
                           @PathVariable("voteType") String voteType,
                           HttpServletRequest request){
        String prevPath = request.getHeader("Referer");
        int voteChange = voteType.equals("upvote") ? 1 : -1;
        voteService.increasePostVote(postId, (int)request.getSession().getAttribute("id"), voteChange);
        return "redirect:" + prevPath;
    }

    @PostMapping("/post/{postId}/unvote")
    public String unvotePost(@PathVariable("postId") int postId,
                             HttpServletRequest request){
        String prevPath = request.getHeader("Referer");
        voteService.unvotePost(postId, (int)request.getSession().getAttribute("id"));
        return "redirect:" + prevPath;
    }

    @PostMapping("/post/{postId}/comment")
    public String comment(@PathVariable("postId") int postId,
                          @ModelAttribute("userComment") CommentDTO commentDTO,
                          HttpServletRequest request){
        String prevPath = request.getHeader("Referer");
        Comment newComment = new Comment();
        newComment.setContent(commentDTO.getContent());
        User author = userService.findById(commentDTO.getAuthorId());
        newComment.setAuthor(author);
        Post post = postService.findById(commentDTO.getPostId());
        newComment.setPost(post);
        commentService.addComment(newComment);
        return "redirect:" + prevPath + "#post-comments";
    }


}
