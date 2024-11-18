package com.social_network.service;

import com.social_network.dao.PostRepository;
import com.social_network.entity.Post;

import com.social_network.entity.Tag;
import com.social_network.entity.User;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@AllArgsConstructor
public class PostService {

    private final int POST_PER_PAGE = 10;

    private PostRepository postRepository;

    public Post save(Post post) {
        return postRepository.save(post);
    }

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

    public Post findById(int id){
        return postRepository.findById(id);
    }

    @Transactional
    public void increaseView(int postId){
        Post post = findById(postId);
        post.setViews(post.getViews() + 1);
        postRepository.save(post);
    }

    public List<Post> getRandowPostsByAuthor(User author, int maxAmount){
        List<Post> posts = postRepository.findByAuthor(author);
        if(posts.size() <= maxAmount) return posts;
        Set<Post> result = new HashSet<>();
        int idx = 0;
        Random rand = new Random();
        while(result.size() < maxAmount){
            idx = rand.nextInt(posts.size());
            result.add(posts.get(idx));
        }
        return result.stream().toList();
    }

    public Page<Post> findByTags(List<Tag> tags, int page){
        Sort sort = Sort.by(Sort.Direction.DESC, "id");
        Pageable pageable = PageRequest.of(page - 1, POST_PER_PAGE, sort);
        return postRepository.findPostByTags(tags, pageable);
    }

    public int countPostsByAuthor(User author){
        return postRepository.countByAuthor(author);
    }

}