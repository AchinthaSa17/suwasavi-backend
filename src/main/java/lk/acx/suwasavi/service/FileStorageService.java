package lk.acx.suwasavi.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileStorageService {

    // This defines where the images will be stored on your server's hard drive
    private final Path root = Paths.get("uploads/prescriptions");

    public void init() {
        try {
            if (!Files.exists(root)) {
                Files.createDirectories(root);
            }
        } catch (IOException e) {
            throw new RuntimeException("Could not initialize folder for upload: " + e.getMessage());
        }
    }

    public String save(MultipartFile file) {
        try {
            // Ensure the directory exists before saving
            init();

            // Generate a unique name to prevent overwriting files with the same name
            String filename = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

            // Copy the file to the target location
            Files.copy(file.getInputStream(), this.root.resolve(filename));

            return filename; // Return the name so we can save it in the database
        } catch (Exception e) {
            throw new RuntimeException("Could not store the file. Error: " + e.getMessage());
        }
    }
}