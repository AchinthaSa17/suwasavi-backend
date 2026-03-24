package lk.acx.suwasavi.controller;

import lk.acx.suwasavi.entity.LabReport;
import lk.acx.suwasavi.repository.LabReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

    @RestController
    @RequestMapping("/api/reports")
    public class LabReportController {
        @Autowired
        private LabReportRepository repository;

        @GetMapping("/user/{uid}")
        public List<LabReport> getReports(@PathVariable String uid) {
            return repository.findByFirebaseUid(uid);
        }
    }

