package com.social_network.controller;

import com.social_network.dto.request.CommentDTO;
import com.social_network.entity.Comment;
import com.social_network.entity.Post;
import com.social_network.entity.Tag;
import com.social_network.entity.User;
import com.social_network.service.*;
import com.social_network.util.MarkdownRenderUtil;
import com.social_network.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
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

    private PostService postService;

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
}
