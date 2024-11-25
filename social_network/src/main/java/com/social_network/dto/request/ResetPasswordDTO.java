package com.social_network.dto.request;

import com.social_network.util.validator.StrongPassword;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResetPasswordDTO {

    @StrongPassword
    private String newPassword;

    private String confirmPassword;

}
