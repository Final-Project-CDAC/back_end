package com.tinyguard.service;

import com.tinyguard.dto.VaccinationRecordDTO;
import com.tinyguard.entity.*;
import com.tinyguard.repository.*;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaccinationRecordService {
    
    private final VaccinationRecordRepository vaccinationRecordRepository;
    private final ChildRepository childRepository;
    private final VaccineRepository vaccineRepository;
    private final ModelMapper modelMapper;
    private final ActivityLogService activityLogService;
    
    public VaccinationRecordDTO createRecord(VaccinationRecordDTO dto) {
        Child child = childRepository.findById(dto.getChildId())
                .orElseThrow(() -> new RuntimeException("Child not found"));
        Vaccine vaccine = vaccineRepository.findById(dto.getVaccineId())
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));
        
        VaccinationRecord record = new VaccinationRecord();
        record.setChild(child);
        record.setVaccine(vaccine);
        record.setDueDate(dto.getDueDate());
        record.setGivenDate(dto.getGivenDate());
        record.setStatus(VaccinationRecord.Status.valueOf(dto.getStatus().toUpperCase()));
        record.setNotes(dto.getNotes());
        
        VaccinationRecord saved = vaccinationRecordRepository.save(record);
        return mapToDTO(saved);
    }
    
    public VaccinationRecordDTO updateRecord(Long id, VaccinationRecordDTO dto) {
        VaccinationRecord record = vaccinationRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        
        record.setDueDate(dto.getDueDate());
        record.setGivenDate(dto.getGivenDate());
        record.setStatus(VaccinationRecord.Status.valueOf(dto.getStatus().toUpperCase()));
        record.setNotes(dto.getNotes());
        
        VaccinationRecord updated = vaccinationRecordRepository.save(record);
        return mapToDTO(updated);
    }
    
    public VaccinationRecordDTO markAsGiven(Long id, LocalDate givenDate) {
        VaccinationRecord record = vaccinationRecordRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Record not found"));
        
        record.setGivenDate(givenDate);
        record.setStatus(VaccinationRecord.Status.COMPLETED);
        
        VaccinationRecord updated = vaccinationRecordRepository.save(record);
        activityLogService.logActivity("VACCINATION_COMPLETED", 
            "Vaccine given: " + record.getVaccine().getName() + " for " + record.getChild().getFullName(), 
            record.getChild().getParent().getEmail());
        return mapToDTO(updated);
    }
    
    public List<VaccinationRecordDTO> getRecordsByChild(Long childId) {
        return vaccinationRecordRepository.findByChildId(childId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public List<VaccinationRecordDTO> getAllVaccinationRecords() {
        return vaccinationRecordRepository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public void deleteRecord(Long id) {
        vaccinationRecordRepository.deleteById(id);
    }
    
    private VaccinationRecordDTO mapToDTO(VaccinationRecord record) {
        VaccinationRecordDTO dto = new VaccinationRecordDTO();
        dto.setId(record.getId());
        dto.setChildId(record.getChild().getId());
        dto.setChildName(record.getChild().getFullName());
        dto.setChildAge(record.getChild().getAgeInMonths());
        dto.setParentName(record.getChild().getParent().getFullName());
        dto.setParentEmail(record.getChild().getParent().getEmail());
        dto.setVaccineId(record.getVaccine().getId());
        dto.setVaccineName(record.getVaccine().getName());
        dto.setAgeMilestone(record.getVaccine().getAgeMilestone());
        dto.setDueDate(record.getDueDate());
        dto.setGivenDate(record.getGivenDate());
        dto.setStatus(record.getStatus().toString());
        dto.setNotes(record.getNotes());
        return dto;
    }
}
