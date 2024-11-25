package com.social_network.dto.request;

import com.social_network.entity.Tag;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
public class PostDTO {

    private int id;

    private String title;

    private String content;

    private List<String> tagNames;

    private String thumbnailUrl;

    public PostDTO() {
        tagNames = new ArrayList<>();
    }

}
