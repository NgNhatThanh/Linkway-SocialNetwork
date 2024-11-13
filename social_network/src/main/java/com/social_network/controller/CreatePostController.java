package com.social_network.controller;

import com.social_network.dto.request.PostDTO;
import com.social_network.entity.Tag;
import com.social_network.entity.User;
import com.social_network.service.PostService;
import com.social_network.service.TagService;
import com.social_network.service.UserService;
import com.social_network.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class CreatePostController {

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

    @GetMapping("/post/edit")
    public String showCreatePostPage(Model model){
        model.addAttribute("postDTO", new PostDTO());
        return "editpost";
    }

    @PostMapping("/post/update")
    public String updatePost(@ModelAttribute("postDTO") PostDTO postDTO){

        System.out.println("ABCDEF: ");
        for(String tagName : postDTO.getTagNames()){
            System.out.println(tagName);
        }

        return "redirect:/";
    }

}
