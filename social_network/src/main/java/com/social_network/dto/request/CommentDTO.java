package com.social_network.dto.request;

import com.social_network.entity.Comment;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CommentDTO {

    private int id;

    private int postId;

    private int parentId;

    private int authorId;

    private String content;

}
