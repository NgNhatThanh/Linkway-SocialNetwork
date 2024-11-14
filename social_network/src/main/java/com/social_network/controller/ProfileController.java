package com.social_network.controller;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

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
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class ProfileController {
    private final UserService userService;
    private final PostService postService;
    private final SecurityUtil securityUtil;
    private final UploadService uploadService;
    private final FollowService followService;
    private TagService tagService;

    @ModelAttribute("followingTags")
    public List<Tag> getFollowingTags() {
        List<Tag> followingTags = null;
        try {
            String username = Objects.requireNonNull(SecurityUtil.getCurrentUser()).getUsername();
            User user = userService.findByUsername(username).get();
            followingTags = tagService.findFollowingTagsByUsername(user.getId());
        } catch (NullPointerException ignored) {
        }
        return followingTags;
    }

    // Search for a user by username or display name
    @GetMapping("/searchUser")
    public String searchUser(@RequestParam("query") String query, Model model) {
        List<User> users = userService.findByUsernameOrDisplayName(query);

        if (!users.isEmpty()) {
            model.addAttribute("users", users);
            model.addAttribute("query", query);
            return "searchResults"; // This will display the search results
        } else {
            model.addAttribute("error", "User not found");
            return "error"; // Ensure error.html template is present
        }
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
            model.addAttribute("totalPages", posts.getTotalPages());
            model.addAttribute("currentPage", page);
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
            model.addAttribute("totalPages", posts.getTotalPages());
            model.addAttribute("currentPage", page);
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
            model.addAttribute("user", user);
        } else {
            // Handle the case when the user is not found
            model.addAttribute("error", "User not found");
            return "error"; // Or redirect to an error page
        }
        return "updateProfile";
    }

    @PostMapping("/updateProfile")
    public String updateProfile(@ModelAttribute("user") @Valid User user, BindingResult bindingResult,
            RedirectAttributes redirectAttributes, @RequestParam("avatar") MultipartFile file) {
        if (bindingResult.hasErrors()) {
            return "updateProfile";
        }
        String username = this.securityUtil.getCurrentUser().getUsername();
        Optional<User> optionalUser = this.userService.findByUsername(username);
        if (optionalUser.isPresent()) {
            User currentUser = optionalUser.get();
            currentUser.setDisplayName(user.getDisplayName());
            currentUser.setIntroduction(user.getIntroduction());
            currentUser.setEmail(user.getEmail());

            if (!file.isEmpty()) {
                currentUser.setAvatarImagePath(this.uploadService.handleSaveUploadFile(file, "avatar"));
            }
            this.userService.save(currentUser);

        } else {
            // Handle the case when the user is not found
            redirectAttributes.addFlashAttribute("error", "User not found");
            return "redirect:/error"; // Or redirect to an error page
        }
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