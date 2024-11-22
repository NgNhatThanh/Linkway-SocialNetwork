package com.social_network.dto;

public class RoleDTO {
    private String roleName;

    // Constructor
    public RoleDTO(String roleName) {
        this.roleName = roleName;
    }

    // Getter and Setter
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }
}
