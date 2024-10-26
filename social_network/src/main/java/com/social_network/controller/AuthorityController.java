package com.social_network.controller;

import com.social_network.dao.AuthorityRepository;
import com.social_network.dto.PageResponse;
import com.social_network.entity.Authority;
import com.social_network.service.AuthoritySevice;
import com.social_network.util.annotation.ApiMessage;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
@AllArgsConstructor
@RequestMapping("/api/authority")
public class AuthorityController {

    private AuthoritySevice authoritySevice;

    private AuthorityRepository authorityRepository;

    @GetMapping("/name={name}")
    @ApiMessage("fetch authority by name")
    public ResponseEntity<Authority> getByName(@PathVariable String name){
        return ResponseEntity
                .ok()
                .body(authoritySevice.findByName(name));
    }

    @GetMapping("/method={method}")
    @ApiMessage("fetch all authorities by method")
    public ResponseEntity<PageResponse<Authority>> getAllByMethod(
            @PathVariable String method,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size){
        return ResponseEntity
                .ok()
                .body(authoritySevice.findAllByMethod(method, page, size));
    }

    @GetMapping("/module={module}")
    @ApiMessage("fetch all authorities by module")
    public ResponseEntity<PageResponse<Authority>> getAllByModule(
            @PathVariable String module,
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ){
        return ResponseEntity
                .ok()
                .body(authoritySevice.findAllByModule(module, page, size));
    }

    @GetMapping("/all")
    @ApiMessage("fetch all authorities")
    public ResponseEntity<PageResponse<Authority>> getAll(
            @RequestParam(value = "page", required = false, defaultValue = "1") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size
    ){
        return ResponseEntity
                .ok()
                .body(authoritySevice.getAll(page, size));
    }

    @GetMapping("/modules")
    @ApiMessage("fetch all authority modules")
    public ResponseEntity<List<String>> getAllModules(){
        return ResponseEntity
                .ok()
                .body(authorityRepository.getAllModules());
    }

}
