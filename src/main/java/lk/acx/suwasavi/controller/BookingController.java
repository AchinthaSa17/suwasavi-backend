package lk.acx.suwasavi.controller;

import lk.acx.suwasavi.entity.Booking;
import lk.acx.suwasavi.repository.BookingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/bookings")
@CrossOrigin(origins = "*")
public class BookingController {

    @Autowired
    private BookingRepository bookingRepository;

    @PostMapping("/create")
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        // Set default status to PENDING as per your DB schema
        booking.setStatus("PENDING");
        return ResponseEntity.ok(bookingRepository.save(booking));
    }

    @GetMapping("/user/{uid}")
    public List<Booking> getMyBookings(@PathVariable String uid) {
        return bookingRepository.findByFirebaseUid(uid);
    }
}
