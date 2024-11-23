package com.social_network.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.social_network.dto.UserDTO;
import com.social_network.entity.Follow;
import com.social_network.entity.Status;
import com.social_network.entity.User;
import com.social_network.service.FollowService;
import com.social_network.service.UserService;
import com.social_network.util.SecurityUtil;

import jakarta.servlet.http.HttpSession;
import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final SecurityUtil securityUtil;
    private final FollowService followService;

    // Fetch current user details from the session
    @GetMapping("/current-user")
    public ResponseEntity<User> getCurrentUser() {
        HttpSession session = securityUtil.getSession();
        String username = (String) session.getAttribute("username");

        if (username == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        Optional<User> userOptional = userService.findByUsername(username);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
        }

        User user = userOptional.get();
        // Use service to convert User to UserDTO
        UserDTO userDTO = userService.convertToDTO(user);
        return ResponseEntity.ok(user);
    }

    @MessageMapping("/user.updateStatus")
    @SendTo("/user/queue/status")
    public User updateStatus(@Payload User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Invalid user data");
        }

        // Log the received payload
        System.out.println("Received update status for user: " + user.getUsername());

        // Fetch existing user from the database and update status
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            User currentUser = existingUser.get();
            currentUser.setStatus(user.getStatus()); // Update the status field
            userService.saveUser(currentUser); // Save updated user
            return currentUser;
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    // WebSocket method to add user and set status to ONLINE
    @MessageMapping("/user.addUser")
    @SendTo("/user/queue/status")
    public User addUser(@Payload User user) {
        if (user == null || user.getUsername() == null || user.getUsername().isEmpty()) {
            throw new IllegalArgumentException("Invalid user data");
        }

        // Log the received payload
        System.out.println("Adding user: " + user.getUsername());

        // Check if the user exists
        Optional<User> existingUser = userService.findByUsername(user.getUsername());
        if (existingUser.isPresent()) {
            // Update the user's status to ONLINE
            User updatedUser = existingUser.get();
            updatedUser.setStatus(Status.ONLINE);
            userService.saveUser(updatedUser);
            return updatedUser;
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    // WebSocket method to disconnect the user and set status to OFFLINE
    @MessageMapping("/user.disconnectUser")
    @SendTo("/user//queue/status") // Updated to user-specific destination
    public User disconnectUser(@Payload User user) {
        if (user == null || user.getId() == 0) {
            throw new IllegalArgumentException("Invalid user data");
        }

        // Log the received payload
        System.out.println("Disconnecting user: " + user.getUsername());

        // Update the user's status to offline
        User existingUser = userService.findById(user.getId());
        if (existingUser != null) {
            existingUser.setStatus(Status.OFFLINE);
            userService.saveUser(existingUser);
            return existingUser;
        } else {
            throw new IllegalArgumentException("User not found");
        }
    }

    // Fetch all connected users
    @GetMapping("/users")
    public ResponseEntity<List<User>> findConnectedUsers() {
        try {
            List<User> connectedUsers = userService.findConnectedUsers();
            return ResponseEntity.ok(connectedUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/users/{username}")
    public ResponseEntity<User> getUser(@PathVariable String username) {
        try {
            Optional<User> userOptional = userService.findByUsername(username);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            User user = userOptional.get();
            UserDTO userDTO = userService.convertToDTO(user);
            return ResponseEntity.ok(user);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Fetch all users that the current logged-in user is following
    @GetMapping("/users/following/{username}")
    public ResponseEntity<List<User>> getFollowingUsers() {
        try {
            HttpSession session = securityUtil.getSession();
            String username = (String) session.getAttribute("username");

            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            Optional<User> userOptional = userService.findByUsername(username);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            User user = userOptional.get();
            List<User> followingUsers = followService.getFollowing(user);
            if (followingUsers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // No followers
            }
            List<UserDTO> followingUserDTOs = userService.convertToDTOList(followingUsers);
            return ResponseEntity.ok(followingUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Fetch all online users (users with ONLINE status)
    @GetMapping("/users/online")
    public ResponseEntity<List<User>> getOnlineUsers() {
        try {
            // Get the list of users who are online
            List<User> onlineUsers = userService.findConnectedUsers();
            if (onlineUsers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // No online users
            }
            List<UserDTO> onlineUserDTOs = userService.convertToDTOList(onlineUsers);
            return ResponseEntity.ok(onlineUsers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @GetMapping("/users/chat")
    public ResponseEntity<List<User>> getChatUsers() {
        try {
            HttpSession session = securityUtil.getSession();
            String username = (String) session.getAttribute("username");

            if (username == null) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            Optional<User> userOptional = userService.findByUsername(username);
            if (userOptional.isEmpty()) {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(null);
            }

            User user = userOptional.get();
            List<User> chatUsers = userService.findUsersChattedWith(username);
            if (chatUsers.isEmpty()) {
                return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null); // No chat users
            }
            List<UserDTO> chatUserDTOs = userService.convertToDTOList(chatUsers);
            return ResponseEntity.ok(chatUsers);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    // Global exception handler for IllegalArgumentException
    @org.springframework.web.bind.annotation.ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    }
}
