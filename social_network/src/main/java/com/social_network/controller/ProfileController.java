package com.social_network.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import com.social_network.dto.request.UserUpdateDTO;
import com.social_network.entity.Follow;
import com.social_network.entity.Post;
import com.social_network.entity.Tag;
import com.social_network.service.*;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.social_network.util.SecurityUtil;

import jakarta.validation.Valid;

import com.social_network.entity.User;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@AllArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final PostService postService;
    private final SecurityUtil securityUtil;
    private final FollowService followService;
    private TagService tagService;

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

    // Show the current user's profile page
    @GetMapping("/profile")
    public String showCurrentUserProfile(Model model,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "followerPage", defaultValue = "1") int followerPage,
            @RequestParam(value = "followingPage", defaultValue = "1") int followingPage) {
        String username = securityUtil.getCurrentUser().getUsername();
        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Page<Post> posts = postService.findByAuthor(user, page);
            Page<Follow> followers = followService.getFollowers(user, followerPage);
            Page<Follow> followings = followService.getFollowing(user, followingPage);
            model.addAttribute("user", user);
            model.addAttribute("postList", posts);
            model.addAttribute("followings", followings);
            model.addAttribute("followers", followers);
            model.addAttribute("isCurrentUser", true); // Indicate that this is the current user's profile
            model.addAttribute("followingsCount", followService.getFollowingCount(user)); // Add followings count
            model.addAttribute("followersCount", followService.getFollowerCount(user)); // Add followers count
            return "profile"; // Matches profile.html template for the current user
        } else {
            model.addAttribute("error", "User not found");
            return "error";
        }
    }

    // Similar changes for the showUserProfile method

    // Show another user's profile by their username
    @GetMapping("/profile/{username}")
    public String showUserProfile(@PathVariable("username") String username,
            Model model,
            @RequestParam(value = "page", defaultValue = "1") int page,
            @RequestParam(value = "followerPage", defaultValue = "1") int followerPage,
            @RequestParam(value = "followingPage", defaultValue = "1") int followingPage) {
        Optional<User> optionalUser = userService.findByUsername(username);
        String currentUsername = securityUtil.getCurrentUser().getUsername();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Page<Post> posts = postService.findByAuthor(user, page);
            Page<Follow> followers = followService.getFollowers(user, followerPage);
            Page<Follow> followings = followService.getFollowing(user, followingPage);
            model.addAttribute("user", user);
            model.addAttribute("postList", posts);
            model.addAttribute("followings", followings);
            model.addAttribute("followers", followers);
            model.addAttribute("isCurrentUser", username.equals(currentUsername)); // Check if this is the current user
            model.addAttribute("isFollowing", followService.isFollowing(currentUsername, username)); // Following status
            model.addAttribute("followingsCount", followService.getFollowingCount(user)); // Add followings count
            model.addAttribute("followersCount", followService.getFollowerCount(user)); // Add followers count
            return "profile"; // Matches profile.html template for other user profiles as well
        } else {
            model.addAttribute("error", "User not found");
            return "error";
        }
    }

    // Follow/unfollow a user
    @PostMapping("/profile/{username}/follow")
    public String toggleFollowUser(@PathVariable("username") String username,
            Model model,
            HttpServletRequest request) {
        String currentUsername = securityUtil.getCurrentUser().getUsername();
        String prevPath = request.getHeader("Referer");

        if (currentUsername.equals(username)) {
            model.addAttribute("error", "You cannot follow yourself.");
            return "error";
        }

        Optional<User> optionalUser = userService.findByUsername(username);
        if (optionalUser.isPresent()) {
            // Check if the current user is already following the target user
            boolean isFollowing = followService.isFollowing(currentUsername, username);

            if (isFollowing) {
                followService.unfollow(currentUsername, username);
            } else {
                followService.followUser(currentUsername, username);
            }

            return "redirect:" + prevPath;
        } else {
            model.addAttribute("error", "User not found");
            return "error";
        }
    }

    @GetMapping("/updateProfile")
    public String updateProfile(Model model) {
        String username = this.securityUtil.getCurrentUser().getUsername();
        Optional<User> optionalUser = this.userService.findByUsername(username);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            UserUpdateDTO userDTO = new UserUpdateDTO();
            userDTO.setId(user.getId());
            userDTO.setDisplayName(user.getDisplayName());
            userDTO.setIntroduction(user.getIntroduction());
            userDTO.setEmail(user.getEmail());
            userDTO.setAvatarImagePath(user.getAvatarImagePath());
            model.addAttribute("userDTO", userDTO);
        } else {
            // Handle the case when the user is not found
            model.addAttribute("error", "User not found");
            return "error"; // Or redirect to an error page
        }
        return "updateProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("userDTO") @Valid UserUpdateDTO userDTO,
            BindingResult bindingResult,
            HttpServletRequest request) {
        if (bindingResult.hasErrors()) {
            return "updateProfile";
        }
        User user = userService.findById(userDTO.getId());
        user.setDisplayName(userDTO.getDisplayName());
        user.setIntroduction(userDTO.getIntroduction());
        user.setEmail(userDTO.getEmail());
        user.setAvatarImagePath(userDTO.getAvatarImagePath());
        request.getSession().setAttribute("avatar", userDTO.getAvatarImagePath());
        return "redirect:/profile";

    }

    @GetMapping("/followers/{username}")
    public String showFollowers(@PathVariable("username") String username, Model model,
            @RequestParam(value = "followerPage", defaultValue = "1") int followerPage) {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (followerPage < 1) {
            followerPage = 1;
            return "redirect:/followers/" + username + "?followerPage=" + followerPage;
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Page<Follow> followers = followService.getFollowers(user, followerPage);
            model.addAttribute("user", user);
            model.addAttribute("followers", followers);
            int totalPages = followers.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
            return "followers"; // Template to show followers
        } else {
            model.addAttribute("error", "User not found");
            return "error";
        }
    }

    @GetMapping("/followings/{username}")
    public String showFollowings(@PathVariable("username") String username, Model model,
            @RequestParam(value = "followingPage", defaultValue = "1") int followingPage) {
        Optional<User> optionalUser = userService.findByUsername(username);
        if (followingPage < 1) {
            followingPage = 1;
            return "redirect:/followings/" + username + "?followingPage=" + followingPage;
        }
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            Page<Follow> followings = followService.getFollowing(user, followingPage);
            model.addAttribute("user", user);
            model.addAttribute("followings", followings);
            int totalPages = followings.getTotalPages();
            if (totalPages > 0) {
                List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                        .boxed()
                        .collect(Collectors.toList());
                model.addAttribute("pageNumbers", pageNumbers);
            }
            return "followings"; // Template to show followings
        } else {
            model.addAttribute("error", "User not found");
            return "error";
        }
    }

}