
package com.social_network.service;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class UploadService {

    private final String uploadDirectory = System.getProperty("user.dir") + "/uploads/images";

    public String handleSaveUploadFile(MultipartFile file, String targetFolder) {
        if (file.isEmpty()) {
            return "";
        }

        String rootPath = uploadDirectory + File.separator + targetFolder;
        String finalName = "";

        try {
            byte[] bytes = file.getBytes();
            File dir = new File(rootPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }
            finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();
            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalName);
            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                stream.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "/uploads/images/" + targetFolder + "/" + finalName;
    }
}
