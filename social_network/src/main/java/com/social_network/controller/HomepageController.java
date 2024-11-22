package com.social_network.controller;

import com.social_network.dto.request.CommentDTO;
import com.social_network.dto.response.UserSearchDTO;
import com.social_network.entity.Comment;
import com.social_network.entity.Post;
import com.social_network.entity.Tag;
import com.social_network.entity.User;
import com.social_network.service.*;
import com.social_network.util.MarkdownRenderUtil;
import com.social_network.util.ModelMapper;
import com.social_network.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import lombok.AllArgsConstructor;
import org.springframework.boot.web.servlet.server.Session;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class HomepageController {

    private final UserService userService;

    private final TagService tagService;

    private final SecurityUtil securityUtil;

    private final FollowService followService;

    private PostService postService;

    @ModelAttribute("followingTags")
    public List<Tag> getAllTags() {
        List<Tag> followingTags = null;
        try {
            String username = Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername();
            User user = userService.findByUsername(username).get();
            followingTags = user.getFollowingTags();
        } catch (NullPointerException ignored) {
        }
        return followingTags;
    }

    @GetMapping("/")
    public String showHomePage(Model model,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        HttpSession session = securityUtil.getSession();
        String currentUsername = (String) session.getAttribute("username");
        Page<Post> postList = null;
        if (currentUsername != null) {
            User user = userService.findByUsername(currentUsername).get();
            postList = postService.getPostByFollowingTagsOrFollowingUsers(user, page);
        } else {
            postList = postService.getAll(page);
        }

        try {
            String username = Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername();
            User user = userService.findByUsername(username).get();
            model.addAttribute("user", user);
        } catch (NullPointerException ignored) {
        }

        model.addAttribute("postList", postList);
        return "home/mainzone";
    }

    @GetMapping("/search")
    public String search(Model model,
            @RequestParam(value = "query") String query,
            @RequestParam(value = "type", defaultValue = "post") String type,
            @RequestParam(value = "sortBy", defaultValue = "relevance") String sortBy,
            @RequestParam(value = "date", defaultValue = "everytime") String date,
            @RequestParam(value = "tagName", defaultValue = "") List<String> tagNames,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        if (type.equals("user")) {
            Page<User> userList = userService.findByKeyword(query, page);
            Page<UserSearchDTO> userDTOList = userList.map(user -> {
                UserSearchDTO dto = ModelMapper.getInstance().map(user, UserSearchDTO.class);
                dto.setFollowersCount(followService.getFollowerCount(user));
                dto.setPostsCount(postService.countPostsByAuthor(user));
                return dto;
            });
            model.addAttribute("userList", userDTOList);
        } else {
            Page<Post> postList = postService.search(query, page, sortBy, date, tagNames);
            model.addAttribute("postList", postList);
        }
        model.addAttribute("query", query);
        model.addAttribute("tagNames", tagNames);
        return "searchresult";
    }
}
