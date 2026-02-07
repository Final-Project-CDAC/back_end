package com.tinyguard.controller;

import com.tinyguard.dto.GrowthRecordDTO;
import com.tinyguard.service.GrowthRecordService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/parent/growth-records")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
public class GrowthRecordController {
    
    private final GrowthRecordService growthRecordService;
    
    @PostMapping
    public ResponseEntity<GrowthRecordDTO> createRecord(@Valid @RequestBody GrowthRecordDTO dto) {
        return ResponseEntity.ok(growthRecordService.createRecord(dto));
    }
    
    @GetMapping("/child/{childId}")
    public ResponseEntity<List<GrowthRecordDTO>> getRecordsByChild(@PathVariable Long childId) {
        return ResponseEntity.ok(growthRecordService.getRecordsByChild(childId));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable Long id) {
        growthRecordService.deleteRecord(id);
        return ResponseEntity.noContent().build();
    }
}
