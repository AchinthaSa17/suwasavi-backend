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

    // Remove "/create" if your Android Retrofit call is just @POST("api/bookings")
    @PostMapping
    public ResponseEntity<Booking> createBooking(@RequestBody Booking booking) {
        if (booking.getStatus() == null) {
            booking.setStatus("PENDING");
        }
        return ResponseEntity.ok(bookingRepository.save(booking));
    }

    @GetMapping("/user/{uid}")
    public List<Booking> getMyBookings(@PathVariable String uid) {
        return bookingRepository.findByFirebaseUid(uid);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<Void> cancelBooking(@PathVariable Long id) {
        return bookingRepository.findById(id).map(booking -> {
            booking.setStatus("CANCELLED");
            bookingRepository.save(booking);
            return ResponseEntity.ok().<Void>build();
        }).orElse(ResponseEntity.notFound().build());
    }
}

