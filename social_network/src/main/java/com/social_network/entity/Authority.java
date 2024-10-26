package com.social_network.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "authorities")
@Getter
@Setter
@NoArgsConstructor
public class Authority {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "name")
    private String name;

    @Column(name = "api_path")
    private String apiPath;

    @Column(name = "http_method")
    private String httpMethod;

    @Column(name = "module")
    private String module;

    @ManyToMany(mappedBy = "authorities", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<Role> roles;

    public Authority(String name, String apiPath, String httpMethod, String module) {
        this.name = name;
        this.apiPath = apiPath;
        this.httpMethod = httpMethod;
        this.module = module;
    }
}
