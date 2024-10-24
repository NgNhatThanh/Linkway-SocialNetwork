package com.social_network.service;

import com.social_network.dao.AuthorityRepository;
import com.social_network.entity.Authority;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthoritySevice {

    private AuthorityRepository authorityRepository;

    public Authority findByName(String name){
        return authorityRepository.findByName(name);
    }

    public Set<Authority> findAllByMethod(String method){
        return authorityRepository.findAllByHttpMethod(method);
    }

    public Set<Authority> findAllByModule(String module){
        return authorityRepository.findAllByModule(module);
    }

}
