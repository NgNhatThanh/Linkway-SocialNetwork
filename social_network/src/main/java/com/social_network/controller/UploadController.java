package com.social_network.controller;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

@Controller
@AllArgsConstructor
public class UploadController {

    private Cloudinary cloudinary;

//    @PostMapping("/upload")
//    @ResponseBody
//    public Map<String, String> uploadImage(@RequestParam("image") MultipartFile image){
////        try {
////            Map r = this.cloudinary.uploader().upload(image.getBytes(),
////                    ObjectUtils.asMap("resource_type", "auto"));
////            return (String) r.get("secure_url");
////        } catch (IOException e) {
////            throw new RuntimeException(e);
////        }
//
//        Map<String, String> response = new HashMap<>();
//        response.put("url", "afdf");
//        return response;
//    }

}
