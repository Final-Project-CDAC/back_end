package com.tinyguard.controller;

import com.tinyguard.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.*;

@RestController
@RequestMapping("/admin/reports")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
public class ReportsController {
    
    private final VaccinationRecordRepository vaccinationRecordRepository;
    private final ChildRepository childRepository;
    
    @GetMapping("/vaccination-coverage")
    public ResponseEntity<List<Map<String, Object>>> getVaccinationCoverage() {
        List<Map<String, Object>> data = new ArrayList<>();
        
        long totalRecords = vaccinationRecordRepository.count();
        long completed = vaccinationRecordRepository.countByStatus(com.tinyguard.entity.VaccinationRecord.Status.COMPLETED);
        long overdue = vaccinationRecordRepository.countByStatus(com.tinyguard.entity.VaccinationRecord.Status.OVERDUE);
        long dueSoon = vaccinationRecordRepository.countByStatus(com.tinyguard.entity.VaccinationRecord.Status.DUE_SOON);
        long upcoming = vaccinationRecordRepository.countByStatus(com.tinyguard.entity.VaccinationRecord.Status.UPCOMING);
        
        Map<String, Object> item = new HashMap<>();
        item.put("age", "All Ages");
        item.put("completed", completed);
        item.put("overdue", overdue);
        item.put("dueSoon", dueSoon);
        item.put("upcoming", upcoming);
        data.add(item);
        
        return ResponseEntity.ok(data);
    }
    
    @GetMapping("/summary")
    public ResponseEntity<Map<String, Object>> getSummary() {
        Map<String, Object> summary = new HashMap<>();
        summary.put("totalChildren", childRepository.count());
        summary.put("totalVaccinations", vaccinationRecordRepository.count());
        summary.put("completedVaccinations", vaccinationRecordRepository.countByStatus(com.tinyguard.entity.VaccinationRecord.Status.COMPLETED));
        summary.put("overdueVaccinations", vaccinationRecordRepository.countByStatus(com.tinyguard.entity.VaccinationRecord.Status.OVERDUE));
        return ResponseEntity.ok(summary);
    }
}
