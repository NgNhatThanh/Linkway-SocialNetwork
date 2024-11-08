package com.social_network.controller;

import com.ibm.icu.text.Normalizer2;
import com.social_network.dao.TagRepository;
import com.social_network.entity.Post;
import com.social_network.entity.Tag;
import com.social_network.entity.User;
import com.social_network.service.*;
import com.social_network.util.MarkdownRenderUtil;
import com.social_network.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.constraints.Null;
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
        long authorFollowers = followService.getFollowerCount(post.getAuthor());
        model.addAttribute("post", post);
        model.addAttribute("postContent", htmlContent);
        model.addAttribute("authorFollowers", authorFollowers);
        model.addAttribute("isCurrentUser",
                post.getAuthor().getUsername().equals(currentUsername));
        model.addAttribute("isFollowing",
                followService.isFollowing(currentUsername,
                                                    post.getAuthor().getUsername()));
        return "home/postpage";
    }

    @PostMapping("/post/{postId}/{voteType}")
    public String votePost(@PathVariable("postId") int postId,
                           @PathVariable("voteType") String voteType,
                           HttpServletRequest request){
        String prevPath = request.getHeader("Referer");
        int voteChange = voteType.equals("upvote") ? 1 : -1;
        String currentUsername = securityUtil.getCurrentUser().getUsername();
        voteService.updatePostVote(postId, currentUsername, voteChange);
        return "redirect:" + prevPath;
    }

}
