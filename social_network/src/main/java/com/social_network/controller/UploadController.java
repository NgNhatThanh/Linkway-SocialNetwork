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
import java.util.UUID;

@Controller
@AllArgsConstructor
public class UploadController {

    private Cloudinary cloudinary;

    @PostMapping("/upload")
    @ResponseBody
    public Map<String, String> uploadImage(@RequestParam("image") MultipartFile image){
        Map<String, String> response = new HashMap<>();
        try {
            String url = this.cloudinary.uploader().upload(image.getBytes(),
                    Map.of("public_id", UUID.randomUUID().toString())).get("url").toString();
            response.put("imageUrl", url);
            return response;
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
