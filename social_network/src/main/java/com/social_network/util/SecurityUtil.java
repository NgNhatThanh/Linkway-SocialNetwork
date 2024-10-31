package com.social_network.util;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Component
public class SecurityUtil {

    public static Optional<String> getCurrentUserLogin() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        return Optional.ofNullable(extractPrincipal(securityContext.getAuthentication()));
    }

    private static String extractPrincipal(Authentication authentication) {
        if(authentication != null){
            Object principal = authentication.getPrincipal();
            if(principal instanceof UserDetails) return ((UserDetails) principal).getUsername();
            if(principal instanceof String) return (String) principal;
        }
        return null;
    }

}
