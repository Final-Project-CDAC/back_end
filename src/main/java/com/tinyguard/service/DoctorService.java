package com.tinyguard.service;

import com.tinyguard.dto.DoctorDTO;
import com.tinyguard.entity.Doctor;
import com.tinyguard.repository.DoctorRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class DoctorService {
    
    private final DoctorRepository doctorRepository;
    
    public List<DoctorDTO> getAllDoctors() {
        return doctorRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public List<DoctorDTO> getActiveDoctors() {
        return doctorRepository.findByStatus(Doctor.Status.ACTIVE).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    public DoctorDTO getDoctorById(Long id) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        return convertToDTO(doctor);
    }
    
    public DoctorDTO createDoctor(DoctorDTO doctorDTO) {
        Doctor doctor = convertToEntity(doctorDTO);
        doctor = doctorRepository.save(doctor);
        return convertToDTO(doctor);
    }
    
    public DoctorDTO updateDoctor(Long id, DoctorDTO doctorDTO) {
        Doctor doctor = doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found"));
        
        doctor.setName(doctorDTO.getName());
        doctor.setSpecialization(doctorDTO.getSpecialization());
        doctor.setPhone(doctorDTO.getPhone());
        doctor.setEmail(doctorDTO.getEmail());
        
        try {
            doctor.setStatus(Doctor.Status.valueOf(doctorDTO.getStatus()));
        } catch (IllegalArgumentException e) {
            throw new RuntimeException("Invalid status value");
        }
        
        doctor = doctorRepository.save(doctor);
        return convertToDTO(doctor);
    }
    
    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }
    
    private DoctorDTO convertToDTO(Doctor doctor) {
        DoctorDTO dto = new DoctorDTO();
        dto.setId(doctor.getId());
        dto.setName(doctor.getName());
        dto.setSpecialization(doctor.getSpecialization());
        dto.setPhone(doctor.getPhone());
        dto.setEmail(doctor.getEmail());
        dto.setStatus(doctor.getStatus().name());
        return dto;
    }
    
    private Doctor convertToEntity(DoctorDTO dto) {
        Doctor doctor = new Doctor();
        doctor.setName(dto.getName());
        doctor.setSpecialization(dto.getSpecialization());
        doctor.setPhone(dto.getPhone());
        doctor.setEmail(dto.getEmail());
        doctor.setStatus(dto.getStatus() != null ? Doctor.Status.valueOf(dto.getStatus()) : Doctor.Status.ACTIVE);
        return doctor;
    }
}
