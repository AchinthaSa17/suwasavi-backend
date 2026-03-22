package lk.acx.suwasavi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Paths;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String uploadPath = Paths.get("uploads").toAbsolutePath().toUri().toString();

        // This maps http://your-url/images/filename.jpg
        // to the physical file in the 'uploads' folder
        registry.addResourceHandler("/images/**")
                .addResourceLocations(uploadPath);
    }
}