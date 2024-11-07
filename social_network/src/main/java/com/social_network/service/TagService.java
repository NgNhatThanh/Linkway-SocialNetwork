package com.social_network.service;

import com.social_network.dao.TagRepository;
import com.social_network.entity.Tag;
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

}
