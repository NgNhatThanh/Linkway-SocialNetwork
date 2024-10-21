package com.social_network.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;

@Entity
@Table(name = "tags")
@Getter
@Setter
public class Tag {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @ManyToMany
    @JoinTable(name = "posts_tags"
                , joinColumns = @JoinColumn(name = "tag_id")
                , inverseJoinColumns = @JoinColumn(name = "post_id"))
    private ArrayList<Post> posts = new ArrayList<>();

}
