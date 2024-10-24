package com.social_network.service;

import com.social_network.dao.RoleRepository;
import com.social_network.entity.Role;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public Role findByName(String name){
        return roleRepository.findByName(name);
    }

    public Role findById(int id){
        return roleRepository.findById(id);
    }

    public Set<Role> findAllByAuthority(String authority){
        return roleRepository.findAllByAuthority(authority);
    }

}
