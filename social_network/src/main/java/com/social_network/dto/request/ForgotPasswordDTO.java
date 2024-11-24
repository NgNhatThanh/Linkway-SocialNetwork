package com.social_network.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ForgotPasswordDTO {

    @Email(message = "Email không đúng định dạng")
    private String email;

}
