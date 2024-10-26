package com.social_network.config;

import com.social_network.entity.Authority;
import com.social_network.entity.Role;
import com.social_network.entity.User;
import com.social_network.exception.AuthorizingException;
import com.social_network.service.UserService;
import com.social_network.util.SecurityUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.HandlerMapping;

import java.util.List;

public class PermissionInterceptor implements HandlerInterceptor {

    @Autowired
    UserService userService;

    @Override
    @Transactional
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response, Object handler)
            throws Exception {

        String path = (String) request.getAttribute(HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE);
        String requestURI = request.getRequestURI();
        String httpMethod = request.getMethod();
        System.out.println(">>> RUN preHandle");
        System.out.println(">>> path= " + path);
        System.out.println(">>> httpMethod= " + httpMethod);
        System.out.println(">>> requestURI= " + requestURI);

        // check permission
        String username = SecurityUtil.getCurrentUserLogin().isPresent() == true
                ? SecurityUtil.getCurrentUserLogin().get()
                : "";

        if (!username.isEmpty()) {
            User user = this.userService.findByUsername(username);
            if (user != null) {
                Role role = user.getRole();
                if (role != null) {
                    List<Authority> permissions = role.getAuthorities();
                    boolean isAllow = permissions.stream().anyMatch(item -> item.getApiPath().equals(path)
                            && item.getHttpMethod().equals(httpMethod));
                    if (isAllow == false) {
                        throw new AuthorizingException("Bạn không có quyền truy cập endpoint này.");
                    }
                } else {
                    throw new AuthorizingException("Bạn không có quyền truy cập endpoint này.");
                }
            }
        } else {
            throw new AuthorizingException("Bạn không có quyền truy cập endpoint này.");
        }
        return true;
    }

}
