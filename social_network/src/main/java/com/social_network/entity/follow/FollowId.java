package com.social_network.entity.follow;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.io.Serializable;

@Getter
@Setter
@AllArgsConstructor
public class FollowId implements Serializable {

    private String followedId;

    private String followingId;

}
