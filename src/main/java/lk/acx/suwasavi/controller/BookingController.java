package lk.acx.suwasavi.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import lk.acx.suwasavi.entity.Booking;
import lk.acx.suwasavi.repository.BookingRepository;
import lk.acx.suwasavi.service.FileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private FileStorageService fileStorageService;

    /**
     * STANDARD BOOKING (Nursing, Physio, etc.)
     * Handles simple JSON requests for services that don't require an image.
     */
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        if (booking.getStatus() == null) {
            booking.setStatus("PENDING");
        }
        return ResponseEntity.ok(bookingRepository.save(booking));
    }

    /**
     * PHARMACY BOOKING (With Prescription Image)
     * Handles Multipart requests containing both a JSON string and a File.
     */
    @PostMapping(value = "/create-with-prescription", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Booking> createWithPrescription(
            @RequestPart("booking") String bookingJson,
            @RequestPart("file") MultipartFile file) {
        try {
            // 1. Validate file existence
            if (file.isEmpty()) {
                return ResponseEntity.badRequest().build();
            }

            // 2. Convert the JSON string from Android back into a Booking object
            ObjectMapper objectMapper = new ObjectMapper();
            Booking booking = objectMapper.readValue(bookingJson, Booking.class);

            // 3. Save the image file and get the generated unique filename
            String fileName = fileStorageService.save(file);

            // 4. Update the booking object metadata
            booking.setPrescriptionImageUrl(fileName);
            booking.setStatus("AWAITING_QUOTATION");

            // 5. Save the record to the MySQL database
            Booking savedBooking = bookingRepository.save(booking);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedBooking);

        } catch (Exception e) {
            // Print the stack trace so you can see the error in the IntelliJ/Eclipse console
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    /**
     * FETCH USER BOOKINGS
     */
    @GetMapping("/user/{uid}")
    public ResponseEntity<List<Booking>> getMyBookings(@PathVariable String uid) {
        List<Booking> bookings = bookingRepository.findByFirebaseUid(uid);
        return ResponseEntity.ok(bookings);
    }

    /**
     * CANCEL A BOOKING
     */
    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}