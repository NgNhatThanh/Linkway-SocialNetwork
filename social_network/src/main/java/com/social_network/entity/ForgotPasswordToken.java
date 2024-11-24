package com.social_network.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.Random;

@Entity
@Table(name = "forgot_password_tokens")
@Getter
@Setter
public class ForgotPasswordToken {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne
    @JoinColumn(name = "requestor_id")
    private User requestor;

    @Column(name = "code")
    private String code;

    @Column(name = "expire_at")
    private Date expireAt;

    @Column(name = "is_used")
    private boolean used = false;

}
