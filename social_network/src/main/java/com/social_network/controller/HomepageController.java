package com.social_network.controller;

import com.social_network.entity.Post;
import com.social_network.entity.Tag;
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

import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class HomepageController {

    private final UserService userService;

    private final TagService tagService;

    private final SecurityUtil securityUtil;

    private PostService postService;

    private MarkdownRenderUtil markdownRenderUtil;

    private FollowService followService;

    private VoteService voteService;

    @ModelAttribute("followingTags")
    public List<Tag> getAllTags() {
        List<Tag> followingTags = null;
        try {
            String username = Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername();
            User user = userService.findByUsername(username).get();
            followingTags = tagService.findFollowingTagsByUsername(user.getId());
        } catch (NullPointerException ignored) {
        }
        return followingTags;
    }

    @GetMapping("/")
    public String showHomePage(Model model,
                               @RequestParam(value = "page", defaultValue = "1") int page) {

        Page<Post> postList = postService.getAll(page);

        try {
            String username = Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername();
            User user = userService.findByUsername(username).get();
            model.addAttribute("user", user);
        } catch (NullPointerException ignored) {
        }

        model.addAttribute("totalPages", postList.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("postList", postList);
        return "home/mainzone";
    }

    @GetMapping("/post/{postId}")
    public String showPostPage(@PathVariable("postId") int postId, Model model){
        postService.increaseView(postId);
        Post post = postService.findById(postId);
        String htmlContent = markdownRenderUtil.convertToHtml(post.getContent());
        String currentUsername = securityUtil.getCurrentUser().getUsername();
        int voteType = voteService.getUserPostVoteType(postId, currentUsername);
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
        return "home/postpage";
    }

    @PostMapping("/post/{postId}/{voteType}")
    public String votePost(@PathVariable("postId") int postId,
                           @PathVariable("voteType") String voteType,
                           HttpServletRequest request){
        String prevPath = request.getHeader("Referer");
        int voteChange = voteType.equals("upvote") ? 1 : -1;
        String currentUsername = securityUtil.getCurrentUser().getUsername();
        voteService.increasePostVote(postId, currentUsername, voteChange);
        return "redirect:" + prevPath;
    }

    @PostMapping("/post/{postId}/unvote")
    public String unvotePost(@PathVariable("postId") int postId,
                             HttpServletRequest request){
        String prevPath = request.getHeader("Referer");
        String currentUsername = securityUtil.getCurrentUser().getUsername();
        voteService.unvotePost(postId, currentUsername);
        return "redirect:" + prevPath;
    }

}
