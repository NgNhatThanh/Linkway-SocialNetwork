package com.social_network.controller;

import com.social_network.entity.Role;
import com.social_network.service.RoleService;
import com.social_network.util.annotation.ApiMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/role")
public class RoleController {

    private RoleService roleService;

    @GetMapping("/auth={authority}")
    @ApiMessage("fetch all roles by authority")
    public ResponseEntity<List<Role>> getAllByAuthority(@PathVariable String authority){
        return ResponseEntity
                .ok()
                .body(roleService.findAllByAuthority(authority));
    }

    @GetMapping("/name={name}")
    @ApiMessage("fetch role by name")
    public ResponseEntity<Role> getByName(@PathVariable String name){
        return ResponseEntity
                .ok()
                .body(roleService.findByName(name));
    }


}
