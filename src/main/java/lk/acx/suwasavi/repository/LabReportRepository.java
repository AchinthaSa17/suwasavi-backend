package lk.acx.suwasavi.repository;

import lk.acx.suwasavi.entity.LabReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LabReportRepository extends JpaRepository<LabReport, Long> {
    List<LabReport> findByFirebaseUid(String firebaseUid);
}
