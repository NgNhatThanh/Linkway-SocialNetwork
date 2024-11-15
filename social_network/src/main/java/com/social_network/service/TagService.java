package com.social_network.service;

import com.social_network.dao.TagRepository;
import com.social_network.entity.Tag;
import com.social_network.entity.User;
import com.social_network.exception.DataNotFoundException;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.util.List;

@Service
@AllArgsConstructor
public class TagService {

    private final int TAG_PER_PAGE = 40;

    private TagRepository tagRepository;

    public List<Tag> findFollowingTagsByUsername(int id){
        return tagRepository.findFollowingTagsByUserId(id);
    }

    public List<Tag> findAll(){
        return tagRepository.findAll();
    }

    public Tag findByName(String name){
        Tag tag = tagRepository.findByName(name);
        if(tag == null) throw new DataNotFoundException("Cannot find tag with name " + name);
        return tag;
    }

    public boolean isFollowing(User user, Tag tag){
        return tagRepository.isFollowing(user.getId(), tag.getId());
    }

    public Page<Tag> getAll(int page){
        Pageable pageable = PageRequest.of(page - 1, TAG_PER_PAGE);
        return tagRepository.findAll(pageable);
    }

    public Page<Tag> findByNameIgnoreCaseContaining(String name, int page){
        Pageable pageable = PageRequest.of(page - 1, TAG_PER_PAGE);
        return tagRepository.findByNameIgnoreCaseContaining(name, pageable);
    }

    @Transactional
    public void follow(User user, Tag tag){
        tagRepository.follow(user.getId(), tag.getId());
    }

    @Transactional
    public void unfollow(User user, Tag tag){
        tagRepository.unfollow(user.getId(), tag.getId());
    }

}
