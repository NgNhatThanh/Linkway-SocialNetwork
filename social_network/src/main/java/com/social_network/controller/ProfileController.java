package com.social_network.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import com.social_network.service.UserService;
import com.social_network.util.SecurityUtil;

import jakarta.validation.Valid;

import com.social_network.entity.Post;
import com.social_network.entity.User;
import com.social_network.service.FollowService;
import com.social_network.service.PostService;
import com.social_network.service.UploadService;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
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
    public String showCurrentUserProfile(Model model) {
        String username = securityUtil.getCurrentUser().getUsername();
        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            model.addAttribute("posts", postService.findByUser(user));
            model.addAttribute("followings", followService.getFollowing(user));
            model.addAttribute("followers", followService.getFollowers(user));
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
    public String showUserProfile(@PathVariable("username") String username, Model model) {
        Optional<User> optionalUser = userService.findByUsername(username);
        String currentUsername = securityUtil.getCurrentUser().getUsername();

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            model.addAttribute("posts", postService.findByUser(user));
            model.addAttribute("followings", followService.getFollowing(user));
            model.addAttribute("followers", followService.getFollowers(user));
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
    public String toggleFollowUser(@PathVariable("username") String username, Model model) {
        String currentUsername = securityUtil.getCurrentUser().getUsername();

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

            return "redirect:/profile/" + username;
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
    public String showFollowers(@PathVariable("username") String username, Model model) {
        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            model.addAttribute("followers", followService.getFollowers(user));
            return "followers"; // Template to show followers
        } else {
            model.addAttribute("error", "User not found");
            return "error";
        }
    }

    @GetMapping("/followings/{username}")
    public String showFollowings(@PathVariable("username") String username, Model model) {
        Optional<User> optionalUser = userService.findByUsername(username);

        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            model.addAttribute("user", user);
            model.addAttribute("followings", followService.getFollowing(user));
            return "followings"; // Template to show followings
        } else {
            model.addAttribute("error", "User not found");
            return "error";
        }
    }

}
