package com.social_network.service;

import com.social_network.dao.AuthorityRepository;
import com.social_network.dao.RoleRepository;
import com.social_network.entity.Authority;
import com.social_network.entity.Role;
import com.social_network.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Set;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    private AuthoritySevice authoritySevice;

    public Role findByName(String name){
        name = name.toLowerCase();
        Role role = roleRepository.findByName(name);
        if(role == null)
            throw new DataNotFoundException("Cannot find role with name: " + name);
        return role;
    }

    public Role findById(int id){
        Role role = roleRepository.findById(id);
        if(role == null)
            throw new DataNotFoundException("Cannot find role with id: " + id);
        return role;
    }

    public List<Role> findAllByAuthority(String authorityName){
        Authority authority = authoritySevice.findByName(authorityName);
        return roleRepository.findAllByAuthority(authorityName);
    }

}
