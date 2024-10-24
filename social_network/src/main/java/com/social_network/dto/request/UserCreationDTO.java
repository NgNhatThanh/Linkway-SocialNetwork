package com.social_network.dto.request;

import jakarta.validation.constraints.Email;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.UniqueElements;

import java.time.LocalDateTime;

@Getter
@Setter
public class UserCreationDTO {

    private String username;

    private String password;

    private String displayName;

    private String email;

    private String introduction;

    private LocalDateTime createdAt;

}
