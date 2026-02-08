package com.tinyguard.controller;

import com.tinyguard.dto.VaccinationRecordDTO;
import com.tinyguard.service.VaccinationRecordService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/vaccination-records")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
public class AdminVaccinationRecordController {
    
    private final VaccinationRecordService vaccinationRecordService;
    
    @GetMapping
    public ResponseEntity<List<VaccinationRecordDTO>> getAllVaccinationRecords() {
        return ResponseEntity.ok(vaccinationRecordService.getAllVaccinationRecords());
    }
}
