package com.social_network.dto;

public class UserDTO {

    // Fields
    private String username;
    private String displayName;

    // No-argument constructor
    public UserDTO() {
        // Default constructor
    }

    // Other constructors, getters, and setters as needed
    public UserDTO(String username, String displayName) {
        this.username = username;
        this.displayName = displayName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
}
