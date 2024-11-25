package com.social_network.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TagResponseDTO {

    private int id;

    private String name;

    private String shortDescription;

    private int postCount;

    private int followerCount;

}
