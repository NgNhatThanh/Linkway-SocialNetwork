package com.social_network.controller;

import com.social_network.dto.request.LoginRequestDTO;
import com.social_network.dto.request.RegisterDTO;
import com.social_network.dto.response.LoginResponseDTO;
import com.social_network.dto.response.UserResponseDTO;
import com.social_network.entity.User;
import com.social_network.service.UserService;
import com.social_network.util.ModelMapper;
import com.social_network.util.SecurityUtil;
import com.social_network.util.validator.RegisterChecked;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.security.Principal;

@Controller
@AllArgsConstructor
public class AuthController {

    UserService userService;

    SecurityUtil securityUtil;

    @GetMapping("/login")
    public String showLoginPage(Model model){
        model.addAttribute("loginUser", new LoginRequestDTO());
        return "loginpage";
    }

    @GetMapping("/register")
    public String showRegisterPage(Model model){
        model.addAttribute("registerUser", new RegisterDTO());
        return "registerpage";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
                                 BindingResult bindingResult,
                                 RedirectAttributes redirectAttributes){

        if(bindingResult.hasErrors()){
            return "registerpage";
        }
        userService.addUser(registerDTO);
        redirectAttributes.addFlashAttribute("registrationSuccess", "Đăng ký thành công");
        return "redirect:/login";
    }

}
