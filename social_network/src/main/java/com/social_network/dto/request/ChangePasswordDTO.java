package com.social_network.dto.request;

import com.social_network.util.validator.StrongPassword;
import jakarta.validation.constraints.AssertTrue;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChangePasswordDTO {

    private String oldPassword;

    @StrongPassword
    private String newPassword;

    private String repeatPassword;

    // @AssertTrue(message = "Mật khẩu xác nhận không khớp.")
    // private boolean isEqual() {
    // return newPassword != null && newPassword.equals(repeatPassword);
    // }

}
