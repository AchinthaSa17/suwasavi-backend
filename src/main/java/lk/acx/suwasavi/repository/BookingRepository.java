package lk.acx.suwasavi.repository;

import lk.acx.suwasavi.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
    // This looks for the 'firebaseUid' field in the Booking entity
    List<Booking> findByFirebaseUid(String firebaseUid);
}