package com.tinyguard.controller;

import com.tinyguard.dto.VaccineDTO;
import com.tinyguard.service.VaccineService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/admin/vaccines")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
public class VaccineController {
    
    private final VaccineService vaccineService;
    
    @PostMapping
    public ResponseEntity<VaccineDTO> createVaccine(@Valid @RequestBody VaccineDTO vaccineDTO) {
        return ResponseEntity.ok(vaccineService.createVaccine(vaccineDTO));
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<VaccineDTO> updateVaccine(@PathVariable Long id, @Valid @RequestBody VaccineDTO vaccineDTO) {
        return ResponseEntity.ok(vaccineService.updateVaccine(id, vaccineDTO));
    }
    
    @GetMapping
    public ResponseEntity<List<VaccineDTO>> getAllVaccines() {
        return ResponseEntity.ok(vaccineService.getAllVaccines());
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<VaccineDTO> getVaccine(@PathVariable Long id) {
        return ResponseEntity.ok(vaccineService.getVaccineById(id));
    }
    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteVaccine(@PathVariable Long id) {
        vaccineService.deleteVaccine(id);
        return ResponseEntity.noContent().build();
    }
}
