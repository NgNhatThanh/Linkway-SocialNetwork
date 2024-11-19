package com.social_network.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

@Getter
@Setter
public class UserUpdateDTO {

    private int id;

    @NotEmpty(message = "Tên hiển thị không được trống")
    private String displayName;

    private String introduction;

    @Email(message = "Email không đúng định dạng")
    private String email;

    private String avatarImagePath;

}
