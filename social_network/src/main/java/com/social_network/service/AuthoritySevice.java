package com.social_network.service;

import com.social_network.dao.AuthorityRepository;
import com.social_network.entity.Authority;
import com.social_network.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class AuthoritySevice {

    private AuthorityRepository authorityRepository;

    public Authority findByName(String name){
        name = name.toLowerCase();
        Authority authority = authorityRepository.findByName(name);
        if(authority == null)
            throw new DataNotFoundException("Cannot find authority with name: " + name);
        return authority;
    }

    public Set<Authority> findAllByMethod(String method){
        method = method.toUpperCase();
        return authorityRepository.findAllByHttpMethod(method);
    }

    public Set<Authority> findAllByModule(String module){
        module = module.toLowerCase();
        return authorityRepository.findAllByModule(module);
    }

    public Set<Authority> findAll(){
        return new HashSet<>(authorityRepository.findAll());
    }

}
