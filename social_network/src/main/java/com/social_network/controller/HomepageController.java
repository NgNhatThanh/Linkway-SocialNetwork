package com.social_network.controller;

import com.social_network.dao.TagRepository;
import com.social_network.entity.Post;
import com.social_network.entity.Tag;
import com.social_network.entity.User;
import com.social_network.service.PostService;
import com.social_network.service.UserService;
import com.social_network.util.SecurityUtil;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomepageController {

    private final UserService userService;
    private TagRepository tagRepository;

    private PostService postService;

    @ModelAttribute("followingTags")
    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }

    @GetMapping("/")
    public String showHomePage(Model model,
                               @RequestParam(value = "page", defaultValue = "1") int page){

        Page<Post> postList = postService.getAll(page);

        String username = SecurityUtil.getCurrentUser().getUsername();

        User user = userService.findByUsername(username);

        model.addAttribute("totalPages", postList.getTotalPages());
        model.addAttribute("currentPage", page);
        model.addAttribute("postList", postList);
        model.addAttribute("user", user);
        return "home/mainzone";
    }


}
