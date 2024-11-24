package com.social_network.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import java.util.Collection;
import java.util.Optional;

@Component
public class SecurityUtil {

    private final HttpServletRequest request;

    public SecurityUtil(HttpServletRequest request) {
        this.request = request;

    }

    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if (authentication != null) {
            Object principal = authentication.getPrincipal();
            if (principal instanceof UserDetails)
                return ((UserDetails) principal).getUsername();
            if (principal instanceof String)
                return (String) principal;
        }
        return null;
    }

    public static UserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }
        return null;
    }

    public static String getRole() {
        UserDetails user = getCurrentUser();
        if (user != null) {
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            if (!authorities.isEmpty()) {
                return authorities.iterator().next().getAuthority();
            }
        }
        return null;
    }

    public static Collection<? extends GrantedAuthority> getRoles() {
        UserDetails user = getCurrentUser();
        if (user != null) {
            return user.getAuthorities();
        }
        return null;
    }

    public HttpSession getSession() {
        return request.getSession();
    }

}
