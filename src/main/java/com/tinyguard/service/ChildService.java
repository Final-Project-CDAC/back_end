package com.tinyguard.service;

import com.tinyguard.dto.ChildDTO;
import com.tinyguard.entity.Child;
import com.tinyguard.entity.User;
import com.tinyguard.entity.Vaccine;
import com.tinyguard.entity.VaccinationRecord;
import com.tinyguard.repository.ChildRepository;
import com.tinyguard.repository.UserRepository;
import com.tinyguard.repository.VaccineRepository;
import com.tinyguard.repository.VaccinationRecordRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ChildService {
    
    private final ChildRepository childRepository;
    private final UserRepository userRepository;
    private final VaccineRepository vaccineRepository;
    private final VaccinationRecordRepository vaccinationRecordRepository;
    private final ModelMapper modelMapper;
    private final ActivityLogService activityLogService;
    
    public ChildDTO createChild(ChildDTO childDTO, String parentEmail) {
        if (childDTO.getFullName() == null || childDTO.getFullName().trim().isEmpty()) {
            throw new RuntimeException("Child name is required");
        }
        if (childDTO.getDateOfBirth() == null) {
            throw new RuntimeException("Date of birth is required");
        }
        if (childDTO.getDateOfBirth().isAfter(LocalDate.now())) {
            throw new RuntimeException("Date of birth cannot be in the future");
        }
        
        User parent = userRepository.findByEmail(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found"));
        
        Child child = modelMapper.map(childDTO, Child.class);
        child.setParent(parent);
        child.setGender(Child.Gender.valueOf(childDTO.getGender().toUpperCase()));
        
        Child saved = childRepository.save(child);
        activityLogService.logActivity("CHILD_ADDED", "New child added: " + saved.getFullName(), parentEmail);
        
        // Auto-generate vaccination records based on child's age
        generateVaccinationRecords(saved);
        
        return modelMapper.map(saved, ChildDTO.class);
    }
    
    private void generateVaccinationRecords(Child child) {
        List<Vaccine> allVaccines = vaccineRepository.findAll();
        LocalDate birthDate = child.getDateOfBirth();
        
        for (Vaccine vaccine : allVaccines) {
            VaccinationRecord record = new VaccinationRecord();
            record.setChild(child);
            record.setVaccine(vaccine);
            
            LocalDate dueDate = birthDate.plusDays(vaccine.getAgeInDays());
            record.setDueDate(dueDate);
            
            // Determine status based on due date
            LocalDate today = LocalDate.now();
            if (dueDate.isBefore(today.minusDays(7))) {
                record.setStatus(VaccinationRecord.Status.OVERDUE);
            } else if (dueDate.isBefore(today.plusDays(14))) {
                record.setStatus(VaccinationRecord.Status.DUE_SOON);
            } else {
                record.setStatus(VaccinationRecord.Status.UPCOMING);
            }
            
            vaccinationRecordRepository.save(record);
        }
    }
    
    public ChildDTO updateChild(Long id, ChildDTO childDTO) {
        Child child = childRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Child not found"));
        
        child.setFullName(childDTO.getFullName());
        child.setDateOfBirth(childDTO.getDateOfBirth());
        child.setGender(Child.Gender.valueOf(childDTO.getGender().toUpperCase()));
        child.setBloodType(childDTO.getBloodType());
        child.setAllergies(childDTO.getAllergies());
        child.setPrimaryContactName(childDTO.getPrimaryContactName());
        child.setPrimaryContactRelation(childDTO.getPrimaryContactRelation());
        child.setPrimaryContactPhone(childDTO.getPrimaryContactPhone());
        child.setPrimaryContactEmail(childDTO.getPrimaryContactEmail());
        child.setSecondaryContactName(childDTO.getSecondaryContactName());
        child.setSecondaryContactRelation(childDTO.getSecondaryContactRelation());
        child.setSecondaryContactPhone(childDTO.getSecondaryContactPhone());
        
        Child updated = childRepository.save(child);
        activityLogService.logActivity("CHILD_UPDATED", "Child profile updated: " + updated.getFullName(), child.getParent().getEmail());
        return modelMapper.map(updated, ChildDTO.class);
    }
    
    public List<ChildDTO> getChildrenByParent(String parentEmail) {
        User parent = userRepository.findByEmail(parentEmail)
                .orElseThrow(() -> new RuntimeException("Parent not found"));
        
        return childRepository.findByParentId(parent.getId()).stream()
                .map(child -> modelMapper.map(child, ChildDTO.class))
                .collect(Collectors.toList());
    }
    
    public ChildDTO getChildById(Long id) {
        Child child = childRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Child not found"));
        return modelMapper.map(child, ChildDTO.class);
    }
    
    public void deleteChild(Long id) {
        childRepository.deleteById(id);
    }
}
