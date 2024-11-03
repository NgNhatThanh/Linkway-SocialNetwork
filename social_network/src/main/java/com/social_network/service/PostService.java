package com.social_network.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.social_network.dao.PostRepository;
import com.social_network.entity.Post;
import com.social_network.entity.User;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class PostService {
    private final PostRepository postRepository;

    public List<Post> findByUser(User user) {
        return postRepository.findByAuthor(user);
    }

    public void save(Post post) {
        postRepository.save(post);
    }

    public void delete(Post post) {
        postRepository.delete(post);
    }

    public List<Post> findAll() {
        return postRepository.findAll();
    }

    public Post findById(int id) {
        return postRepository.findById(id);
    }

}
