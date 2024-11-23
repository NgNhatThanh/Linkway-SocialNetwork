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

import java.util.Optional;

import org.springframework.security.crypto.password.PasswordEncoder;
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
    private PasswordEncoder passwordEncoder;

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

    @GetMapping("/change-password")
    public String showChangePasswordPage(Model model) {
        model.addAttribute("changePassword", new ChangePasswordDTO());
        return "changepassword";
    }

    @PostMapping("/change-password")
    public String handleChangePassword(@ModelAttribute("changePassword") @Valid ChangePasswordDTO changePasswordDTO,
            BindingResult bindingResult,
            RedirectAttributes redirectAttributes) {

        if (bindingResult.hasErrors()) {
            return "auth/changepassword";
        }

        HttpSession session = securityUtil.getSession();
        String username = (String) session.getAttribute("username");
        if (username == null) {
            redirectAttributes.addFlashAttribute("error", "Phiên làm việc không hợp lệ.");
            return "redirect:/login";
        }

        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            redirectAttributes.addFlashAttribute("error", "Người dùng không tồn tại.");
            return "redirect:/change-password";
        }

        User user = optionalUser.get();

        if (!passwordEncoder.matches(changePasswordDTO.getOldPassword(), user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu cũ không đúng.");
            return "redirect:/change-password";
        }

        if (passwordEncoder.matches(changePasswordDTO.getNewPassword(), user.getPassword())) {
            redirectAttributes.addFlashAttribute("error", "Mật khẩu mới không được trùng với mật khẩu cũ.");
            return "redirect:/change-password";
        }

        userService.changePassword(user, changePasswordDTO.getNewPassword());
        redirectAttributes.addFlashAttribute("redirectParam", "Đổi mật khẩu thành công. Vui lòng đăng nhập lại.");
        return "redirect:/login";
    }

}
