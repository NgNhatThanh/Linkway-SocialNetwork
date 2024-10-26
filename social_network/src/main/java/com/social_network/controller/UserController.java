package com.social_network.controller;

import com.social_network.dao.UserRepository;
import com.social_network.dto.PageResponse;
import com.social_network.dto.request.UserCreationDTO;
import com.social_network.dto.response.UserResponseDTO;
import com.social_network.entity.User;
import com.social_network.exception.DataNotFoundException;
import com.social_network.service.UserService;
import com.social_network.util.ModelMapper;
import com.social_network.util.annotation.ApiMessage;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    @PostMapping("/register")
    @ApiMessage("register")
    public ResponseEntity<UserResponseDTO> addUser(@RequestBody @Valid UserCreationDTO newUser){
        User user = userService.addUser(newUser);
        return ResponseEntity
                .ok()
                .body(userService.convertToUserResponseDTO(user));
    }

    @GetMapping("/id={id}")
    @ApiMessage("fetch user by id")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable int id){
        User user = userService.findById(id);
        return ResponseEntity
                .ok()
                .body(userService.convertToUserResponseDTO(user));
    }

    @GetMapping("/username={username}")
    @ApiMessage("fetch user by username")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username){
        User user = userService.findByUsername(username);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(userService.convertToUserResponseDTO(user));
    }

    @PutMapping("/update")
    @ApiMessage("update user")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserCreationDTO updatedUser){
        User user = userService.updateUser(updatedUser);
        return ResponseEntity
                .status(HttpStatus.ACCEPTED)
                .body(userService.convertToUserResponseDTO(user));
    }

    @GetMapping("/all")
    @ApiMessage("fetch all users")
    public ResponseEntity<PageResponse<UserResponseDTO>> getAll(
        @RequestParam(value = "page", required = false, defaultValue = "1") int page,
        @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ){
        return ResponseEntity
                .ok()
                .body(userService.getAll(page, size));
    }

}
