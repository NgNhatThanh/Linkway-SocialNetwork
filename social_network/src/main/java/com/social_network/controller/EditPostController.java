package com.social_network.controller;

import com.social_network.dto.request.PostDTO;
import com.social_network.entity.Post;
import com.social_network.entity.Tag;
import com.social_network.entity.User;
import com.social_network.service.PostService;
import com.social_network.service.TagService;
import com.social_network.service.UserService;
import com.social_network.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Objects;

@Controller
@AllArgsConstructor
public class EditPostController {

    private final UserService userService;

    private final TagService tagService;

    private PostService postService;

    @ModelAttribute("followingTags")
    public List<Tag> getFollowingTags() {
        List<Tag> followingTags = null;
        try {
            String username = Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername();
            User user = userService.findByUsername(username).get();
            followingTags = user.getFollowingTags();
        } catch (NullPointerException ignored) {
        }
        return followingTags;
    }

    @GetMapping("/post/edit")
    public String showCreatePostPage(Model model){
        model.addAttribute("postDTO", new PostDTO());
        return "editpost";
    }

    @GetMapping("/post/edit/{postId}")
    public String showEditPostPage(Model model, @PathVariable int postId){
        Post post = postService.findById(postId);
        PostDTO postDTO = new PostDTO();
        postDTO.setId(post.getId());
        postDTO.setTitle(post.getTitle());
        postDTO.setContent(post.getContent());
        for(Tag tag : post.getTags()){
            postDTO.getTagNames().add(tag.getName());
        }
        model.addAttribute("postDTO", postDTO);
        return "editpost";
    }

    @PostMapping("/post/update")
    public String updatePost(@ModelAttribute("postDTO") PostDTO postDTO,
                             HttpServletRequest request){

        Post newPost;

        if(postDTO.getId() > 0){
            newPost = postService.findById(postDTO.getId());
            newPost.setUpdatedAt(Date.from(Instant.now()));
        }
        else{
            newPost = new Post();
            User author = userService.findById((Integer) request.getSession().getAttribute("id"));
            newPost.setAuthor(author);
            newPost.setCreatedAt(Date.from(Instant.now()));
        }

        newPost.setTitle(postDTO.getTitle());
        newPost.setContent(postDTO.getContent());
        List<Tag> tags = new ArrayList<>();
        for(String tagName : postDTO.getTagNames()){
            Tag tag = tagService.findByName(tagName);
            tags.add(tag);
        }
        newPost.setTags(tags);
        newPost = postService.save(newPost);
        return "redirect:/post/" + newPost.getId();
    }

    @GetMapping("/api/tags")
    @ResponseBody
    public List<Tag> getAllTags(){
        return tagService.findAll();
    }

}
