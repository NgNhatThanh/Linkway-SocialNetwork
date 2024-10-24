package com.social_network.controller;

import com.social_network.dao.AuthorityRepository;
import com.social_network.service.AuthoritySevice;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class AuthorityController {

    private AuthoritySevice authoritySevice;

    private AuthorityRepository authorityRepository;

    @GetMapping("/authorities/module")
    public Set<String> getAllModules(){
        return authorityRepository.getAllModules();
    }

}
