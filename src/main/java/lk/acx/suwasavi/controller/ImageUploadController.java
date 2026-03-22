package lk.acx.suwasavi.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/api/admin")
public class ImageUploadController {

    @Value("${app.image.base-url}")
    private String baseUrl;

    // The path to your 'uploads' folder
    private final String uploadDir = "uploads/";

    @PostMapping("/upload-service-image")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) {
        try {
            // 1. Ensure the directory exists
            File directory = new File(uploadDir);
            if (!directory.exists()) directory.mkdirs();

            // 2. Save the file with its original name
            String fileName = file.getOriginalFilename();
            Path path = Paths.get(uploadDir + fileName);
            Files.write(path, file.getBytes());

            // 3. Return the accessible URL
            // Replace with your current ngrok or local IP
            String fileUrl = baseUrl + fileName;
            return ResponseEntity.ok(fileUrl);

        } catch (IOException e) {
            return ResponseEntity.status(500).body("Upload failed: " + e.getMessage());
        }
    }
}
