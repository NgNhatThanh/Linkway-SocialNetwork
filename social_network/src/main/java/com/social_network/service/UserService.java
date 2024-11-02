package com.social_network.service;

import com.social_network.dao.UserRepository;
import com.social_network.dto.PageResponse;
import com.social_network.dto.request.RegisterDTO;
import com.social_network.dto.response.UserResponseDTO;
import com.social_network.entity.Role;
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
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private RoleService roleService;

    @Transactional
    public User addUser(RegisterDTO newUser){
        User user = ModelMapper.getInstance()
                    .map(newUser, User.class);

//        if(userRepository.existsByEmail(user.getEmail()))
//            throw new DataExistedException("Email has been taken");
//
//        if(userRepository.existsByUsername(user.getUsername()))
//            throw new DataExistedException("Username has been taken");

        String encodedPassword = BCryptEncoder.getInstance().encode(user.getPassword());
        user.setPassword(encodedPassword);
        Role role = roleService.findByName("USER");
        user.setRole(role);
        user.setAvatarImagePath("path to default profile picture");
        user.setCreatedAt(Date.from(Instant.now()));
        this.userRepository.save(user);
        return user;
    }

    public User findById(int id){
        User user = userRepository.findById(id);
        if(user == null) throw new DataNotFoundException("Could not find user with id: " + id);
        return user;
    }
//
//    public User updateUser(UserCreationDTO updatedUser){
//        User user = findByUsername(updatedUser.getUsername());
//
//        return userRepository.save(user);
//    }

    public User findByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user == null) throw new DataNotFoundException("Could not find user with username: " + username);
        return user;
    }

    public UserResponseDTO convertToUserResponseDTO(User user){
        return ModelMapper.getInstance()
                .map(user, UserResponseDTO.class);
    }

    public PageResponse<UserResponseDTO> getAll(int page, int size){
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

    public void updateUserToken(String username, String token){
        User user = findByUsername(username);
        user.setRefreshToken(token);
        userRepository.save(user);
    }
}
