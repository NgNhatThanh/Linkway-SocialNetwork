package com.social_network.service;

import com.social_network.dao.PostRepository;
import com.social_network.entity.Post;

import com.social_network.entity.User;
import lombok.AllArgsConstructor;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {

    private final int POST_PER_PAGE = 10;

    private PostRepository postRepository;

    public Page<Post> getAll(int page) {
        Pageable pageable = PageRequest.of(page - 1, POST_PER_PAGE);
        return postRepository.findAll(pageable);
    }

    public Page<Post> getByUser(User user, int page) {
        Pageable pageable = PageRequest.of(page - 1, POST_PER_PAGE);
        return postRepository.findByAuthor(user, pageable);
    }

    public Page<Post> findByAuthor(User author, int page) {
        Pageable pageable = PageRequest.of(page - 1, POST_PER_PAGE);
        return postRepository.findByAuthor(author, pageable);
    }

}