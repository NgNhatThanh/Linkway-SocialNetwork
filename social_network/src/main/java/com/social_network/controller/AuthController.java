package com.social_network.controller;

import com.social_network.dao.UserRepository;
import com.social_network.dto.request.ChangePasswordDTO;
import com.social_network.dto.request.LoginRequestDTO;
import com.social_network.dto.request.RegisterDTO;
import com.social_network.entity.User;
import com.social_network.service.UserService;
import com.social_network.util.BCryptEncoder;
import com.social_network.util.SecurityUtil;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@AllArgsConstructor
public class AuthController {

    private final UserRepository userRepository;
    private UserService userService;
    private SecurityUtil securityUtil;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginUser", new LoginRequestDTO());
        return "auth/loginpage";
    }

    // @PostMapping("/login")
    // public String handleLogin(@ModelAttribute("loginUser") @Valid LoginRequestDTO
    // loginRequestDTO,
    // BindingResult bindingResult,
    // RedirectAttributes redirectAttributes,
    // HttpSession session) {
    // if (bindingResult.hasErrors()) {
    // return "auth/loginpage";
    // }

    // User user = userService.findByUsername(loginRequestDTO.getUsername()).get();
    // if (user != null) {
    // session.setAttribute("username", user.getUsername());
    // session.setAttribute("avatarImagePath", user.getAvatarImagePath());
    // return "redirect:/";
    // } else {
    // redirectAttributes.addFlashAttribute("error", "Invalid username or
    // password");
    // return "redirect:/login";
    // }
    // }

    @GetMapping("/register")
    public String showRegisterPage(Model model) {
        model.addAttribute("registerUser", new RegisterDTO());
        return "auth/registerpage";
    }

    @PostMapping("/register")
    public String handleRegister(@ModelAttribute("registerUser") @Valid RegisterDTO registerDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "auth/registerpage";
        }

        userService.addUser(registerDTO);
        redirectAttributes.addFlashAttribute("redirectParam", "Đăng ký thành công");
        return "redirect:/login";
    }

}
