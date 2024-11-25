package com.social_network.dto.request;

import com.social_network.util.validator.RegisterChecked;
import com.social_network.util.validator.StrongPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@RegisterChecked
public class RegisterDTO {

    @NotNull(message = "Username không được trống")
    private String username;

    @StrongPassword
    private String password;

    private String confirmPassword;

    @Email(message = "Email không đúng định dạng")
    private String email;

    @NotNull(message = "Tên hiển thị không được để trống")
    private String displayName;

}
