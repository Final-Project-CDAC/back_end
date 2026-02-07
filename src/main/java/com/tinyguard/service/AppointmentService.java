package com.tinyguard.service;

import com.tinyguard.dto.AppointmentDTO;
import com.tinyguard.entity.*;
import com.tinyguard.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AppointmentService {
    
    private final AppointmentRepository appointmentRepository;
    private final ChildRepository childRepository;
    private final DoctorRepository doctorRepository;
    private final ModelMapper modelMapper;
    
    public AppointmentDTO createAppointment(AppointmentDTO dto) {
        Child child = childRepository.findById(dto.getChildId())
                .orElseThrow(() -> new RuntimeException("Child not found"));
        
        Appointment appointment = new Appointment();
        appointment.setChild(child);
        
        if (dto.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            appointment.setDoctor(doctor);
        }
        
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setNotes(dto.getNotes());
        appointment.setStatus(Appointment.Status.valueOf(dto.getStatus().toUpperCase()));
        
        Appointment saved = appointmentRepository.save(appointment);
        return manualMapToDTO(saved);
    }
    
    public AppointmentDTO updateAppointment(Long id, AppointmentDTO dto) {
        Appointment appointment = appointmentRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Appointment not found"));
        
        if (dto.getDoctorId() != null) {
            Doctor doctor = doctorRepository.findById(dto.getDoctorId())
                    .orElseThrow(() -> new RuntimeException("Doctor not found"));
            appointment.setDoctor(doctor);
        }
        
        appointment.setAppointmentDate(dto.getAppointmentDate());
        appointment.setAppointmentTime(dto.getAppointmentTime());
        appointment.setNotes(dto.getNotes());
        appointment.setSummary(dto.getSummary());
        appointment.setStatus(Appointment.Status.valueOf(dto.getStatus().toUpperCase()));
        
        Appointment updated = appointmentRepository.save(appointment);
        return manualMapToDTO(updated);
    }
    
    public List<AppointmentDTO> getAppointmentsByChild(Long childId) {
        return appointmentRepository.findByChildId(childId).stream()
                .map(this::manualMapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<AppointmentDTO> getAllAppointments() {
        return appointmentRepository.findAll().stream()
                .map(this::manualMapToDTO)
                .collect(Collectors.toList());
    }
    
    public void deleteAppointment(Long id) {
        appointmentRepository.deleteById(id);
    }
    
    private AppointmentDTO manualMapToDTO(Appointment appointment) {
        AppointmentDTO dto = new AppointmentDTO();
        dto.setId(appointment.getId());
        dto.setChildId(appointment.getChild().getId());
        dto.setChildName(appointment.getChild().getFullName());
        dto.setAppointmentDate(appointment.getAppointmentDate());
        dto.setAppointmentTime(appointment.getAppointmentTime());
        dto.setNotes(appointment.getNotes());
        dto.setSummary(appointment.getSummary());
        dto.setStatus(appointment.getStatus().name());
        if (appointment.getDoctor() != null) {
            dto.setDoctorId(appointment.getDoctor().getId());
            dto.setDoctorName(appointment.getDoctor().getName());
        }
        return dto;
    }
}
