package com.social_network.dto.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserSearchDTO {

    private int id;

    private String username;

    private String displayName;

    private String introduction;

    private String avatarImagePath;

    private int followersCount;

    private int postsCount;

}
