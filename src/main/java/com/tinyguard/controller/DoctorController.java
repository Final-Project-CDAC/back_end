package com.tinyguard.controller;

import com.tinyguard.dto.DoctorDTO;
import com.tinyguard.service.DoctorService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:5174", "http://localhost:3000"}, allowCredentials = "true")
public class DoctorController {
    
    private final DoctorService doctorService;
    
    @GetMapping("/parent/doctors")
    public ResponseEntity<List<DoctorDTO>> getAllDoctorsForParent() {
        return ResponseEntity.ok(doctorService.getActiveDoctors());
    }
    
    @GetMapping("/admin/doctors")
    public ResponseEntity<List<DoctorDTO>> getAllDoctors() {
        return ResponseEntity.ok(doctorService.getAllDoctors());
    }
    
    @GetMapping("/admin/doctors/{id}")
    public ResponseEntity<DoctorDTO> getDoctorById(@PathVariable Long id) {
        return ResponseEntity.ok(doctorService.getDoctorById(id));
    }
    
    @PostMapping("/admin/doctors")
    public ResponseEntity<DoctorDTO> createDoctor(@RequestBody DoctorDTO doctorDTO) {
        return ResponseEntity.ok(doctorService.createDoctor(doctorDTO));
    }
    
    @PutMapping("/admin/doctors/{id}")
    public ResponseEntity<DoctorDTO> updateDoctor(@PathVariable Long id, @RequestBody DoctorDTO doctorDTO) {
        return ResponseEntity.ok(doctorService.updateDoctor(id, doctorDTO));
    }
    
    @DeleteMapping("/admin/doctors/{id}")
    public ResponseEntity<Void> deleteDoctor(@PathVariable Long id) {
        doctorService.deleteDoctor(id);
        return ResponseEntity.ok().build();
    }
}
