package com.social_network.controller;

import com.social_network.dao.UserRepository;
import com.social_network.dto.request.UserCreationDTO;
import com.social_network.dto.response.UserResponseDTO;
import com.social_network.exception.DataNotFoundException;
import com.social_network.service.UserService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@AllArgsConstructor
public class UserController {

    private UserService userService;

    private UserRepository userRepository;

    @PostMapping("/user/register")
    public UserResponseDTO addUser(@RequestBody UserCreationDTO user){
        return userService.addUser(user);
    }

    @GetMapping("/user/id={id}")
    public ResponseEntity<Object> getUserById(@PathVariable int id){
        try {
            return new ResponseEntity<>(userService.findById(id), HttpStatus.OK);
        }
        catch (DataNotFoundException e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/user/username={username}")
    public UserResponseDTO getUserByUsername(@PathVariable String username){
        return userService.findByUsername(username);
    }

}
