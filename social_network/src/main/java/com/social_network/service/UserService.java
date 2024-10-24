package com.social_network.service;

import com.social_network.dao.UserRepository;
import com.social_network.dto.request.UserCreationDTO;
import com.social_network.dto.response.UserResponseDTO;
import com.social_network.entity.Role;
import com.social_network.entity.User;
import com.social_network.exception.DataExistedException;
import com.social_network.exception.DataNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserService {

    private UserRepository userRepository;

    private RoleService roleService;

    private ModelMapper mapper;

    private PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO addUser(UserCreationDTO newUser){
        User user = mapper.map(newUser, User.class);

        if(userRepository.existsByEmail(user.getEmail()))
            throw new DataExistedException("Email has been taken");

        if(userRepository.existsByUsername(user.getUsername()))
            throw new DataExistedException("Username has been taken");

        String encodedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encodedPassword);
        Role role = roleService.findByName("user");
        user.setRole(role);
        this.userRepository.save(user);
        return mapper.map(user, UserResponseDTO.class);
    }

    public UserResponseDTO findById(int id){
        User user = userRepository.findById(id);
        if(user == null) throw new DataNotFoundException("Could not find user with id: " + id);
        return mapper.map(user, UserResponseDTO.class);
    }

    public UserResponseDTO findByUsername(String username){
        User user = userRepository.findByUsername(username);
        if(user == null) throw new DataNotFoundException("Could not find user with username: " + username);
        return mapper.map(user, UserResponseDTO.class);
    }

}
