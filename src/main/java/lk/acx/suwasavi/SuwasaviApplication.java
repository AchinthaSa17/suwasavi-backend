package lk.acx.suwasavi;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import jakarta.annotation.PostConstruct;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.ClassPathResource; // Add this import

import java.io.InputStream;

@SpringBootApplication
public class SuwasaviApplication {

    public static void main(String[] args) {
        SpringApplication.run(SuwasaviApplication.class, args);
    }

    @PostConstruct
    public void initializeFirebase() {
        try {
            // Use ClassPathResource to find the file inside the resources folder automatically
            ClassPathResource resource = new ClassPathResource("serviceAccountKey.json");
            InputStream serviceAccount = resource.getInputStream();

            FirebaseOptions options = FirebaseOptions.builder()
                    .setCredentials(GoogleCredentials.fromStream(serviceAccount))
                    .build();

            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                System.out.println("Firebase Admin SDK Initialized Successfully!");
            }
        } catch (Exception e) {
            System.err.println("Firebase Initialization Error: " + e.getMessage());
            e.printStackTrace();
        }
    }
}