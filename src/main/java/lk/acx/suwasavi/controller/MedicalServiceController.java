package lk.acx.suwasavi.controller;

import lk.acx.suwasavi.entity.MedicalService;
import lk.acx.suwasavi.repository.MedicalServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
@CrossOrigin(origins = "*")
public class MedicalServiceController {

    @Autowired
    private MedicalServiceRepository repository;

    @Value("${app.image.base-url}")
    private String baseUrl;

    // FIX: Explicitly set the path to "" so /api/services works correctly
    @GetMapping("")
    public List<MedicalService> getAllServices() {
        List<MedicalService> services = repository.findAll();

        // Dynamically attach the base URL to the filename for the list
        services.forEach(service -> {
            String fileName = service.getImageUrl();
            if (fileName != null && !fileName.startsWith("http")) {
                service.setImageUrl(baseUrl + fileName);
            }
        });

        return services;
    }

    // This method handles /api/services/{id}
    @GetMapping("/{id}")
    public ResponseEntity<MedicalService> getServiceById(@PathVariable Long id) {
        return repository.findById(id).map(service -> {
            // Dynamically attach the base URL to the filename for a single item
            String fileName = service.getImageUrl();
            if (fileName != null && !fileName.startsWith("http")) {
                service.setImageUrl(baseUrl + fileName);
            }
            return ResponseEntity.ok(service);
        }).orElse(ResponseEntity.notFound().build());
    }
}