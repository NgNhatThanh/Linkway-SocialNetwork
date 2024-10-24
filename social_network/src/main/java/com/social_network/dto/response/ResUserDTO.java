package com.social_network.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUserDTO {
    private int id;
    private String username;
    private String email;
    private String name;
    private String address;
    private String avatarUrl;
    private String bio;
    private LocalDateTime createdAt;
    private Instant updatedAt;
    private RoleDTO role;

    public ResUserDTO(int id, String username, String email, String name, String avatarFileName,
            String bio, LocalDateTime createdAt, RoleDTO role, String baseURI) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.name = name;
        this.avatarUrl = baseURI + "/avatars/" + avatarFileName;
        this.bio = bio;
        this.createdAt = createdAt;
        this.role = role;
    }

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class RoleDTO {
        private int id;
        private String roleName;
    }
}
