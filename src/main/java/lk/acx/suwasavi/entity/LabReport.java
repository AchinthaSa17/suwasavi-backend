package lk.acx.suwasavi.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "lab_reports")
public class LabReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "firebase_uid") // Matches your SQL column name
    private String firebaseUid;

    @Column(name = "test_name")
    private String testName;

    @Column(name = "lab_name")
    private String labName;

    @Column(name = "report_date")
    private String reportDate;

    private String status;
    private String fileUrl;

    // 1. MANDATORY: Default constructor for JPA
    public LabReport() {
    }

    // 2. Existing Constructor
    public LabReport(Long id, String firebaseUid, String testName, String labName, String reportDate, String status, String fileUrl) {
        this.id = id;
        this.firebaseUid = firebaseUid;
        this.testName = testName;
        this.labName = labName;
        this.reportDate = reportDate;
        this.status = status;
        this.fileUrl = fileUrl;
    }

    // Getters and Setters (Keep these as they are)
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getFirebaseUid() { return firebaseUid; }
    public void setFirebaseUid(String firebaseUid) { this.firebaseUid = firebaseUid; }
    public String getTestName() { return testName; }
    public void setTestName(String testName) { this.testName = testName; }
    public String getLabName() { return labName; }
    public void setLabName(String labName) { this.labName = labName; }
    public String getReportDate() { return reportDate; }
    public void setReportDate(String reportDate) { this.reportDate = reportDate; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getFileUrl() { return fileUrl; }
    public void setFileUrl(String fileUrl) { this.fileUrl = fileUrl; }
}