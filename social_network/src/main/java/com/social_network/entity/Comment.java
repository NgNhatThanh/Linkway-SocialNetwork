package com.social_network.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;

@Entity
@Table(name = "comments")
@Getter
@Setter
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "content")
    private String content;

    @ManyToOne
    @JoinColumn(name = "parent_id")
    @JsonIgnore
    private Comment parentComment;

    @OneToMany(mappedBy = "parentComment"
            , cascade = CascadeType.REMOVE
            , fetch = FetchType.LAZY)
    private ArrayList<Comment> replies = new ArrayList<>();

    @Column(name = "has_child")
    private boolean has_child;

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @Column(name = "was_updated")
    private boolean wasUpdated;

}
