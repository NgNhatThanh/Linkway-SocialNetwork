package com.social_network.controller;

import com.social_network.dao.TagRepository;
import com.social_network.entity.Tag;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.List;

@Controller
@AllArgsConstructor
public class HomepageController {

    TagRepository tagRepository;

    @ModelAttribute("tags")
    public List<Tag> getAllTags(){
        return tagRepository.findAll();
    }

    @GetMapping("/")
    public String showHomePage(Model model){
        return "homepage";
    }


}
