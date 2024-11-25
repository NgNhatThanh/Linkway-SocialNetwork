package com.social_network.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

public class BCryptEncoder {

    private static PasswordEncoder encoder;

    public static PasswordEncoder getInstance() {
        if (encoder == null)
            encoder = new BCryptPasswordEncoder();
        return encoder;
    }

    public static boolean matches(String oldPassword, String password) {
        return getInstance().matches(oldPassword, password);
    }

}
