package com.social_network.service;

import com.social_network.dao.AuthorityRepository;
import com.social_network.dto.PageResponse;
import com.social_network.entity.Authority;
import com.social_network.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

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

    public PageResponse<Authority> findAllByMethod(String method, int page, int size){
        method = method.toUpperCase();

        Sort sort = Sort.by("id").ascending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        var pageData = authorityRepository.findAllByHttpMethod(method, pageable);

        return PageResponse.<Authority>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent())
                .build();
    }

    public PageResponse<Authority> findAllByModule(String module, int page, int size){
        module = module.toLowerCase();
        Sort sort = Sort.by("id").ascending();

        Pageable pageable = PageRequest.of(page - 1, size, sort);

        var pageData = authorityRepository.findAllByModule(module, pageable);

        return PageResponse.<Authority>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent())
                .build();
    }

    public PageResponse<Authority> getAll(int page, int size){

        Sort sort = Sort.by("id").ascending();
        Pageable pageable = PageRequest.of(page - 1, size, sort);

        var pageData = authorityRepository.findAll(pageable);

        return PageResponse.<Authority>builder()
                .currentPage(page)
                .pageSize(pageData.getSize())
                .totalPage(pageData.getTotalPages())
                .totalElement(pageData.getTotalElements())
                .data(pageData.getContent())
                .build();
    }

}
