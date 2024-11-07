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
        // Don't upload if file is empty
        if (file.isEmpty()) {
            return "";
        }

        // Define the directory for storing the uploaded file
        String rootPath = uploadDirectory + File.separator + targetFolder;
        String finalName = "";

        try {
            byte[] bytes = file.getBytes();

            // Create directory if it does not exist
            File dir = new File(rootPath);
            if (!dir.exists()) {
                dir.mkdirs();
            }

            // Generate a unique filename using the current timestamp
            finalName = System.currentTimeMillis() + "-" + file.getOriginalFilename();

            // Create the file on the server
            File serverFile = new File(dir.getAbsolutePath() + File.separator + finalName);

            // Save the file to the server
            try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(serverFile))) {
                stream.write(bytes);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Return the relative path for accessing the uploaded file
        return "/uploads/images/" + targetFolder + "/" + finalName;
    }
}
