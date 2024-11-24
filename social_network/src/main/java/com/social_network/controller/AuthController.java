package com.social_network.controller;

import com.social_network.dao.UserRepository;
import com.social_network.dto.request.*;
import com.social_network.entity.ForgotPasswordToken;
import com.social_network.entity.User;
import com.social_network.service.EmailService;
import com.social_network.service.ForgotPassTokenService;
import com.social_network.service.UserService;
import com.social_network.util.BCryptEncoder;
import com.social_network.util.SecurityUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Optional;

import org.modelmapper.internal.bytebuddy.utility.RandomString;
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
    private final EmailService emailService;
    private final ForgotPassTokenService forgotPassTokenService;
    private UserService userService;
    private SecurityUtil securityUtil;
    private PasswordEncoder passwordEncoder;

    @GetMapping("/login")
    public String showLoginPage(Model model) {
        model.addAttribute("loginUser", new LoginRequestDTO());
        return "auth/loginpage";
    }

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
            return "changepassword";
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
            bindingResult.rejectValue("oldPassword", "password.invalid", "Mật khẩu cũ không đúng.");
            return "changepassword";
        }

        if (passwordEncoder.matches(changePasswordDTO.getNewPassword(), user.getPassword())) {
            bindingResult.rejectValue("newPassword", "password.same", "Mật khẩu mới không được trùng với mật khẩu cũ.");
            return "changepassword";
        }

        if (!changePasswordDTO.getNewPassword().equals(changePasswordDTO.getRepeatPassword())) {
            bindingResult.rejectValue("repeatPassword", "password.mismatch", "Mật khẩu xác nhận không khớp.");
            return "changepassword";
        }

        userService.changePassword(user, changePasswordDTO.getNewPassword());
        redirectAttributes.addFlashAttribute("redirectParam", "Đổi mật khẩu thành công. Vui lòng đăng nhập lại.");
        return "redirect:/login";
    }


    @GetMapping("/forgot-password")
    public String showForgotPasswordPage(Model model) {
        model.addAttribute("dto", new ForgotPasswordDTO());
        return "auth/forgotPassword";
    }

    @PostMapping("/forgot-password")
    public String confirmEmail(@ModelAttribute("dto") @Valid ForgotPasswordDTO dto,
                               BindingResult bindingResult,
                               HttpServletRequest request,
                               RedirectAttributes redirectAttributes) {
        if(bindingResult.hasErrors()) {
            return "auth/forgotPassword";
        }
        User user = userService.findByEmail(dto.getEmail());
        if(user == null){
            bindingResult.rejectValue("email", "EmailNotExist",
                    "Email không tồn tại");
            return "auth/forgotPassword";
        }

        ForgotPasswordToken token = new ForgotPasswordToken();
        token.setCode(RandomString.make(30));
        token.setRequestor(user);
        token.setExpireAt(Date.from(Instant.now().plus(10, ChronoUnit.MINUTES)));
        forgotPassTokenService.save(token);

        String link = request.getRequestURL().toString()
                .replace(request.getServletPath(), "") + "/reset-password?token=" + token.getCode();

        emailService.sendEmail(dto.getEmail(),
                "noreply",
                "Hãy truy cập vào link sau để đặt lại mật khẩu: " + link);
        redirectAttributes.addFlashAttribute("redirectParam", "Email khôi phục đã được gửi đến địa chỉ của bạn");
        return "redirect:/forgot-password";
    }

    @GetMapping("/reset-password")
    public String showResetPasswordPage(Model model,
                                        @RequestParam(value = "token") String token) {
        model.addAttribute("token", token);
        model.addAttribute("dto", new ResetPasswordDTO());
        return "auth/resetPassword";
    }

    @PostMapping("/reset-password")
    public String confirmReset(@ModelAttribute("dto") @Valid ResetPasswordDTO dto,
                               BindingResult bindingResult,
                               @RequestParam(value = "token") String tokenCode,
                               RedirectAttributes redirectAttributes,
                               HttpServletRequest request,
                               Model model) {
        String urlSite = request.getRequestURL().toString();
        if(bindingResult.hasErrors()){
            model.addAttribute("token", tokenCode);
            return "auth/resetPassword";
        }
        if(!dto.getConfirmPassword().equals(dto.getNewPassword())) {
            bindingResult.rejectValue("confirmPassword", "password.invalid", "Mật khẩu không trùng khớp");
            model.addAttribute("token", tokenCode);
            return "auth/resetPassword";
        }
        ForgotPasswordToken token = forgotPassTokenService.findByCode(tokenCode);
        if(token == null){
            redirectAttributes.addFlashAttribute("redirectParam", "Link không chính xác");
            return "redirect:/" + urlSite;
        }
        if(token.isUsed()){
            redirectAttributes.addFlashAttribute("redirectParam", "Link đã được sử dụng");
            return "redirect:/" + urlSite;
        }
        if(token.getExpireAt().before(Date.from(Instant.now()))){
            redirectAttributes.addFlashAttribute("redirectParam", "Link đã hết hạn");
            return "redirect:/" + urlSite;
        }
        String hassPw = BCryptEncoder.getInstance().encode(dto.getNewPassword());
        token.getRequestor().setPassword(hassPw);
        userService.save(token.getRequestor());
        redirectAttributes.addFlashAttribute("redirectParam", "Đặt lại mật khẩu thành công");
        return "redirect:/login";
    }

}
