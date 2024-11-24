package com.social_network.dao;

import com.social_network.entity.ForgotPasswordToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ForgotPassTokenRepo extends JpaRepository<ForgotPasswordToken, Integer> {
    ForgotPasswordToken findByCode(String code);
}
