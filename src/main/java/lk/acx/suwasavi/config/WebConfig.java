package lk.acx.suwasavi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // This maps http://.../api/services/images/filename.png
        // to the physical folder: uploads/services/
        String serviceImagesPath = Paths.get("uploads/services").toFile().getAbsolutePath();

        registry.addResourceHandler("/api/services/images/**")
                .addResourceLocations("file:" + serviceImagesPath + "/");

        // Also map your prescriptions folder for later
        String prescriptionPath = Paths.get("uploads/prescriptions").toFile().getAbsolutePath();
        registry.addResourceHandler("/api/bookings/prescriptions/**")
                .addResourceLocations("file:" + prescriptionPath + "/");
    }
}