package com.social_network.service;

import com.social_network.dao.PostRepository;
import com.social_network.entity.Post;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class PostService {

    private PostRepository postRepository;

    public Page<Post> getAll(int page){
        Pageable pageable = PageRequest.of(page - 1, 2);
        return postRepository.findAll(pageable);
    }

}
