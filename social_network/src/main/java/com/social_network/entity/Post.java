package com.social_network.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Entity
@Table(name = "posts")
@Getter
@Setter
public class Post {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Transient
    private String htmlContent;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private User author;

    @Column(name = "thumbnail_url")
    private String thumbnailUrl;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "updated_at")
    private Date updatedAt;

    @ManyToMany
    @JoinTable(name = "posts_tags"
            , joinColumns = @JoinColumn(name = "post_id")
            , inverseJoinColumns = @JoinColumn(name = "tag_id"))
    private List<Tag> tags;

    @Column(name = "views")
    private int views;

    @Formula("(SELECT COUNT(*) FROM post_votes v WHERE v.post_id = id AND v.vote_type = 1)")
    private int upvotes;

    @Formula("(SELECT COUNT(*) FROM post_votes v WHERE v.post_id = id AND v.vote_type = -1)")
    private int downvotes;

    @OneToMany(mappedBy = "post", cascade = CascadeType.REMOVE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Comment> comments;

}
