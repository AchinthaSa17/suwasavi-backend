package lk.acx.suwasavi.controller;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.FirebaseMessagingException;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import lk.acx.suwasavi.entity.Booking;
import lk.acx.suwasavi.entity.LabReport;
import lk.acx.suwasavi.repository.BookingRepository;
import lk.acx.suwasavi.repository.LabReportRepository;
import lk.acx.suwasavi.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/reports")
@CrossOrigin(origins = "*")
public class LabReportController {

    @Autowired
    private LabReportRepository reportRepository;

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private NotificationService notificationService;

    @PostMapping("/add-by-uid")
    @CrossOrigin(origins = "*")
    public ResponseEntity<?> addReportByUid(@RequestBody LabReport report) {
        // 1. Save the report
        LabReport savedReport = reportRepository.save(report);

        // 2. Find the user's FCM token from their most recent booking
        // This assumes you want to notify the user's current device
        List<Booking> userBookings = bookingRepository.findByFirebaseUid(report.getFirebaseUid());

        if (!userBookings.isEmpty()) {
            // Get the token from the most recent booking
            String token = userBookings.get(userBookings.size() - 1).getFcmToken();
            if (token != null) {
                notificationService.sendReportNotification(
                        token,
                        "Lab Report Ready",
                        "Your results for " + report.getTestName() + " are now available.",
                        savedReport.getId()
                );
            }
        }

        return ResponseEntity.ok(savedReport);
    }
    @GetMapping("/user/{uid}")
    public ResponseEntity<List<LabReport>> getUserReports(@PathVariable String uid) {
        List<LabReport> reports = reportRepository.findByFirebaseUid(uid);
        if (reports.isEmpty()) {
            return ResponseEntity.noContent().build(); // Returns 204 if no reports found
        }
        return ResponseEntity.ok(reports); // Returns 200 with the list
    }
}




