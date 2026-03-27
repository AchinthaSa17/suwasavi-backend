package lk.acx.suwasavi.controller;

import lk.acx.suwasavi.dto.AdminLoginRequest;
import lk.acx.suwasavi.dto.QuotationRequest;
import lk.acx.suwasavi.entity.Booking;
import lk.acx.suwasavi.repository.BookingRepository;
import lk.acx.suwasavi.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/admin")
@CrossOrigin(origins = "*") // Allows your Web Admin Portal to connect
public class AdminController {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AdminLoginRequest request) {
        // Hardcoded for project simplicity, or check against admin table
        if ("admin@suwasavi.lk".equals(request.getUsername()) && "admin123".equals(request.getPassword())) {
            return ResponseEntity.ok(Map.of("message", "Login Successful"));
        }
        return ResponseEntity.status(401).body("Invalid Credentials");
    }

    @GetMapping("/pending-prescriptions")
    public List<Booking> getPending() {
        // Fetches all pharmacy/lab bookings where status is 'AWAITING_QUOTATION'
        return bookingRepository.findByStatus("AWAITING_QUOTATION");
    }

    @PostMapping("/approve-quotation")
    public ResponseEntity<?> approveQuotation(@RequestBody QuotationRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        // Update MySQL record
        booking.setPrice(request.getPrice());
        booking.setStatus(request.getStatus() != null ? request.getStatus() : "READY");
        bookingRepository.save(booking);

        // Trigger the Broadcast Notification to the Handheld Device
        // Note: You must ensure 'fcmToken' is saved in your Booking or User model
        if (booking.getFcmToken() != null) {
            notificationService.sendBroadcast(
                    booking.getFcmToken(),
                    "SuwaSavi: Prescription Ready",
                    "Your quotation for " + booking.getServiceName() + " is LKR " + request.getPrice()
            );
        }

        return ResponseEntity.ok(Map.of("message", "Quotation sent and user notified"));
    }
}