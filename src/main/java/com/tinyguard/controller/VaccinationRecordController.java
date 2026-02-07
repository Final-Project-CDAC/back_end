package com.tinyguard.controller;

import com.tinyguard.dto.VaccinationRecordDTO;
import com.tinyguard.service.VaccinationRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/parent/vaccination-records")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
public class VaccinationRecordController {
    
    private final VaccinationRecordService vaccinationRecordService;
    
    @PostMapping
    public ResponseEntity<VaccinationRecordDTO> createRecord(@Valid @RequestBody VaccinationRecordDTO dto) {
        return ResponseEntity.ok(vaccinationRecordService.createRecord(dto));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VaccinationRecordDTO> updateRecord(@PathVariable Long id, @Valid @RequestBody VaccinationRecordDTO dto) {
        return ResponseEntity.ok(vaccinationRecordService.updateRecord(id, dto));
    }
    
    @PutMapping("/{id}/mark-given")
    public ResponseEntity<VaccinationRecordDTO> markAsGiven(@PathVariable Long id, @RequestParam String date) {
        try {
            return ResponseEntity.ok(vaccinationRecordService.markAsGiven(id, LocalDate.parse(date)));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }
    
    @GetMapping("/child/{childId}")
    public ResponseEntity<List<VaccinationRecordDTO>> getRecordsByChild(@PathVariable Long childId) {
        return ResponseEntity.ok(vaccinationRecordService.getRecordsByChild(childId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        vaccinationRecordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
