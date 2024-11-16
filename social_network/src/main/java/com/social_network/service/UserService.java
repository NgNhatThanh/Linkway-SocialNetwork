package com.social_network.service;

import com.social_network.chat.ChatMessage;
import com.social_network.chatroom.ChatRoom;
import com.social_network.chatroom.ChatRoomService;
import com.social_network.dao.UserRepository;
import com.social_network.dto.PageResponse;
import com.social_network.dto.UserDTO;
import com.social_network.dto.request.RegisterDTO;
import com.social_network.dto.response.UserResponseDTO;
import com.social_network.entity.Role;
import com.social_network.entity.Status;
import com.social_network.entity.User;
import com.social_network.exception.DataExistedException;
import com.social_network.exception.DataNotFoundException;
import com.social_network.util.BCryptEncoder;
import com.social_network.util.ModelMapper;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.Date;
import java.util.HashSet;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private RoleService roleService;
    private ChatRoomService chatRoomService;

    @Transactional
    public User addUser(RegisterDTO newUser) {
        User user = ModelMapper.getInstance()
                .map(newUser, User.class);

        // if(userRepository.existsByEmail(user.getEmail()))
        // throw new DataExistedException("Email has been taken");
        //
        // if(userRepository.existsByUsername(user.getUsername()))
        // throw new DataExistedException("Username has been taken");

        String encodedPassword = BCryptEncoder.getInstance().encode(user.getPassword());
        user.setPassword(encodedPassword);
        Role role = roleService.findByName("USER");
        user.setRole(role);
        user.setAvatarImagePath("path to default profile picture");
        user.setCreatedAt(Date.from(Instant.now()));
        this.userRepository.save(user);
        return user;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findById(int id) {
        User user = userRepository.findById(id).get();
        if (user == null)
            throw new DataNotFoundException("Could not find user with id: " + id);
        return user;
    }
    //
    // public User updateUser(UserCreationDTO updatedUser){
    // User user = findByUsername(updatedUser.getUsername());
    //
    // return userRepository.save(user);
    // }

    public Optional<User> findByUsername(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        if (user.isEmpty())
            throw new DataNotFoundException("Could not find user with username: " + username);
        return user;
    }

    public List<User> findByUsernameOrDisplayName(String query) {
        List<User> allUsers = userRepository.findAll();

        // Return users whose username or display name contains the query (case
        // insensitive)
        return allUsers.stream()
                .filter(user -> user.getUsername().toLowerCase().contains(query.toLowerCase()) ||
                        user.getDisplayName().toLowerCase().contains(query.toLowerCase()))
                .collect(Collectors.toList());
    }

    public UserResponseDTO convertToUserResponseDTO(User user) {
        return ModelMapper.getInstance()
                .map(user, UserResponseDTO.class);
    }

    public PageResponse<UserResponseDTO> getAll(int page, int size) {
        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);
        var pageData = userRepository.findAll(pageable);
        return PageResponse.<UserResponseDTO>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent().stream().map(user -> convertToUserResponseDTO(user)).toList())
                .build();
    }

    public User getCurrentUser() {
        // Get the authentication object
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        // Assuming you have a method to convert Spring Security User to your custom
        // User entity
        if (authentication != null && authentication.getPrincipal() instanceof User) {
            // Custom logic to map Spring Security User to your custom User entity
            User principal = (User) authentication.getPrincipal();

            // Now, fetch your custom User from your repository based on principal details,
            // like username
            User customUser = userRepository.findByUsername(principal.getUsername()).get();
            return customUser;
        }

        return null;
    }

    public void updateUserToken(String username, String token) {
        User user = findByUsername(username).get();
        userRepository.save(user);
    }

    public UserDTO convertToDTO(User user) {
        return ModelMapper.getInstance()
                .map(user, UserDTO.class);
    }

    public void saveUser(User user) {
        if (user.getId() == 0 || !userRepository.existsById(user.getId())) {
            // Set status to ONLINE only for new users
            user.setStatus(Status.ONLINE);
        }
        userRepository.save(user); // Save the user in the repository
    }

    // Ngắt kết nối và thiết lập trạng thái OFFLINE
    public void disconnect(User user) {
        // Find the user by ID
        Optional<User> storedUser = userRepository.findById(user.getId());

        if (storedUser.isPresent()) {
            // Set the status of the user to OFFLINE
            User updatedUser = storedUser.get();
            updatedUser.setStatus(Status.OFFLINE);

            // Save the user with the updated status
            userRepository.save(updatedUser);
        } else {
            // Optionally, handle the case when the user is not found
            // For example, you could log the error or throw a custom exception
            throw new DataNotFoundException("Could not find user with id: " + user.getId());
        }
    }

    // Tìm danh sách người dùng đang ONLINE
    public List<User> findConnectedUsers() {
        // Return the list of users who are online
        return userRepository.findAllByStatus(Status.ONLINE);
    }

    public List<UserDTO> convertToDTOList(List<User> followingUsers) {
        return followingUsers.stream()
                .map(user -> convertToDTO(user))
                .collect(Collectors.toList());
    }

    // Phương thức tìm danh sách người dùng đã từng nhắn tin với user hiện tại
    public List<User> findUsersChattedWith(String userId) {
        // Lấy tất cả chat rooms của user hiện tại
        List<ChatRoom> chatRooms = chatRoomService.getChatRoomsBySenderId(userId);

        // Lọc ra những chat room mà user hiện tại đã từng nhắn tin với
        List<User> users = chatRooms.stream()
                .map(chatRoom -> {
                    String recipientId = chatRoom.getRecipientId();
                    return userRepository.findByUsername(recipientId).get();
                })
                .collect(Collectors.toList());

        // Trả về danh sách người dùng đã từng nhắn tin với user hiện tại
        return users;
    }
}
