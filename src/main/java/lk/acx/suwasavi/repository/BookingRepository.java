package lk.acx.suwasavi.repository;

import lk.acx.suwasavi.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> findByFirebaseUid(String firebaseUid);
}
