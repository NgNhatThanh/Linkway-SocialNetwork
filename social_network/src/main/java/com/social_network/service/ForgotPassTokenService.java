package com.social_network.service;

import com.social_network.dao.ForgotPassTokenRepo;
import com.social_network.entity.ForgotPasswordToken;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class ForgotPassTokenService {

    private ForgotPassTokenRepo forgotPassTokenRepo;

    public void save(ForgotPasswordToken token){
        forgotPassTokenRepo.save(token);
    }

    public ForgotPasswordToken findByCode(String token){
        return forgotPassTokenRepo.findByCode(token);
    }

}
