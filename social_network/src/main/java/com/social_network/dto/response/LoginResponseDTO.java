package com.social_network.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginResponseDTO {

    private int id;

    private String username;

    private String token;

}
