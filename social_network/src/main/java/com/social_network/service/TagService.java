package com.social_network.service;

import com.social_network.dao.TagRepository;
import com.social_network.entity.Tag;
import com.social_network.exception.DataNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TagService {

    private TagRepository tagRepository;

    public List<Tag> findFollowingTagsByUsername(int id){
        return tagRepository.findFollowingTagsByUserId(id);
    }

    public List<Tag> findAll(){
        return tagRepository.findAll();
    }

    public Tag findByName(String name){
        Tag tag = tagRepository.findByName(name);
        System.out.println("Vao day");
        if(tag == null) throw new DataNotFoundException("Cannot find tag with name " + name);
        return tag;
    }

}
