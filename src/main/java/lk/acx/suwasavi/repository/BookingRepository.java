package lk.acx.suwasavi.repository;

import lk.acx.suwasavi.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Finds bookings for a specific user (already in your code)
    List<Booking> findByFirebaseUid(String firebaseUid);

    // NEW: Used by Admin Panel to fetch prescriptions with 'AWAITING_QUOTATION' status
    List<Booking> findByStatus(String status);
}