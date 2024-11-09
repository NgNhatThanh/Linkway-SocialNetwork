package com.social_network.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Formula;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Comment> replies = new HashSet<>();

    @Column(name = "has_child")
    private boolean has_child;

    @Column(name = "created_at")
    private Date createdAt;

    @Column(name = "was_updated")
    private boolean wasUpdated;

    @Formula("(SELECT COUNT(*) FROM comment_votes c WHERE c.comment_id = id AND c.vote_type = 1)")
    private int upvotes;

    @Formula("(SELECT COUNT(*) FROM comment_votes c WHERE c.comment_id = id AND c.vote_type = -1)")
    private int downvotes;

}
