package com.social_network.dto.response;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class UserResponseDTO {

    private String username;

    private String displayName;

    private String avatarImagePath;

    private String introduction;

    private LocalDateTime createdAt;

}
