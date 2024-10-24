package com.social_network.controller;

import com.social_network.dao.AuthorityRepository;
import com.social_network.entity.Authority;
import com.social_network.service.AuthoritySevice;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @GetMapping("/authority/name={name}")
    public ResponseEntity<Authority> getByName(@PathVariable String name){
        return ResponseEntity
                .ok()
                .body(authoritySevice.findByName(name));
    }

    @GetMapping("/authority/method={method}")
    public ResponseEntity<Set<Authority>> getAllByMethod(@PathVariable String method){
        return ResponseEntity
                .ok()
                .body(authoritySevice.findAllByMethod(method));
    }

    @GetMapping("authority/module={module}")
    public ResponseEntity<Set<Authority>> getAllByModule(@PathVariable String module){
        return ResponseEntity
                .ok()
                .body(authoritySevice.findAllByModule(module));
    }

    @GetMapping("/authorities")
    public ResponseEntity<Set<Authority>> getAll(){
        return ResponseEntity
                .ok()
                .body(authoritySevice.findAll());
    }

    @GetMapping("/authority/modules")
    public ResponseEntity<Set<String>> getAllModules(){
        return ResponseEntity
                .ok()
                .body(authorityRepository.getAllModules());
    }

}
