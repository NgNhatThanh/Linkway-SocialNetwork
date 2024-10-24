package com.social_network.controller;

import com.social_network.entity.Role;
import com.social_network.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api")
public class RoleController {

    private RoleService roleService;

    @GetMapping("/role/auth={authority}")
    public Set<Role> getAllByAuthority(@PathVariable String authority){
        return roleService.findAllByAuthority(authority);
    }

    @GetMapping("/role/name={name}")
    public Role getByName(@PathVariable String name){
        return roleService.findByName(name);
    }


}
