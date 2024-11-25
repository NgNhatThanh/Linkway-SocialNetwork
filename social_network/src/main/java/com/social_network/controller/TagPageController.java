package com.social_network.controller;

import com.social_network.entity.Post;
import com.social_network.entity.Tag;
import com.social_network.entity.User;
import com.social_network.service.PostService;
import com.social_network.service.TagService;
import com.social_network.service.UserService;
import com.social_network.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class TagPageController {

    private PostService postService;

    private TagService tagService;

    private UserService userService;

    @ModelAttribute("followingTags")
    public List<Tag> getFollwingTags() {
        List<Tag> followingTags = null;
        try {
            String username = Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername();
            User user = userService.findByUsername(username).get();
            followingTags = user.getFollowingTags();
        } catch (NullPointerException ignored) {
        }
        return followingTags;
    }

    @GetMapping("/tag/{tagName}")
    public String showTagPage(@PathVariable String tagName,
                              @RequestParam(value = "page", defaultValue = "1") int page,
                              Model model) {
        Tag tag = tagService.findByName(tagName);
        String username = Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername();
        User user = userService.findByUsername(username).get();
        boolean isFollowing = tagService.isFollowing(user, tag);
        Page<Post> posts = postService.findByTags(Collections.singletonList(tag), page);
        model.addAttribute("tag", tag);
        model.addAttribute("isFollowing", isFollowing);
        model.addAttribute("postList", posts);
        return "tag/tagpage";
    }

    @PostMapping("/tag/{tagName}/follow")
    public String followTag(@PathVariable String tagName){
        Tag tag = tagService.findByName(tagName);
        String username = Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername();
        User user = userService.findByUsername(username).get();
        tagService.follow(user, tag);
        return "redirect:/tag/" + tagName;
    }

    @PostMapping("/tag/{tagName}/unfollow")
    public String unfollowTag(@PathVariable String tagName){
        Tag tag = tagService.findByName(tagName);
        String username = Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername();
        User user = userService.findByUsername(username).get();
        tagService.unfollow(user, tag);
        return "redirect:/tag/" + tagName;
    }

    @GetMapping("/tags")
    public String showTagsPage(Model model,
                               @RequestParam(value = "page", defaultValue = "1") int page,
                               @RequestParam(value = "query", required = false) String query) {
        Page<Tag> tagList;
        if(query != null && !query.trim().isEmpty()){
            model.addAttribute("query", query);
            tagList = tagService.findByNameIgnoreCaseContaining(query, page);
        }
        else{
            tagList = tagService.getAll(page);
        }
        model.addAttribute("tagList", tagList);
        return "tag/tags";
    }

}
