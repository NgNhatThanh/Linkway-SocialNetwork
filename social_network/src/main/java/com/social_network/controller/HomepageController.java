package com.social_network.controller;

import com.social_network.dao.TagRepository;
import com.social_network.entity.Post;
import com.social_network.entity.Tag;
import com.social_network.entity.User;
import com.social_network.service.PostService;
import com.social_network.service.TagService;
import com.social_network.service.UserService;
import com.social_network.util.SecurityUtil;
import jakarta.validation.constraints.Null;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class HomepageController {

    private final UserService userService;

    private final TagService tagService;

    private PostService postService;

    @ModelAttribute("followingTags")
    public List<Tag> getAllTags(){
        List<Tag> followingTags = null;
        try{
            String username = Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername();
            User user = userService.findByUsername(username);
            followingTags = tagService.findFollowingTagsByUsername(user.getId());
        }
        catch (NullPointerException ignored){}
        return followingTags;
    }

    @GetMapping("/")
    public String showHomePage(Model model,
                               @RequestParam(value = "page", defaultValue = "1") int page){

        Page<Post> postList = postService.getAll(page);

        try{
            String username = Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername();
            User user = userService.findByUsername(username);
            model.addAttribute("user", user);
        }
        catch (NullPointerException ignored){}

        model.addAttribute("totalPages", postList.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("postList", postList);
        return "home/mainzone";
    }


}
