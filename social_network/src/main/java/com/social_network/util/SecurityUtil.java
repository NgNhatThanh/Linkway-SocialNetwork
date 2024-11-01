package com.social_network.util;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class SecurityUtil {
    // Method to get the current user from the security context
    public static UserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (principal instanceof UserDetails) {
            return (UserDetails) principal;
        }
        return null; // return null if not authenticated
    }

    // Method to get the first role of the current user
    public static String getRole() {
        UserDetails user = getCurrentUser();
        if (user != null) {
            Collection<? extends GrantedAuthority> authorities = user.getAuthorities();
            if (!authorities.isEmpty()) {
                return authorities.iterator().next().getAuthority(); // Get the first role
            }
        }
        return null; // Return null if user is null or has no roles
    }

    // Optional: Method to get all roles as a List
    public static Collection<? extends GrantedAuthority> getRoles() {
        UserDetails user = getCurrentUser();
        if (user != null) {
            return user.getAuthorities(); // Return all roles
        }
        return null; // Return null if user is null
    }
}
