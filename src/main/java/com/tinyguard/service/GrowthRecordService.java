package com.tinyguard.service;

import com.tinyguard.dto.GrowthRecordDTO;
import com.tinyguard.entity.Child;
import com.tinyguard.entity.GrowthRecord;
import com.tinyguard.repository.ChildRepository;
import com.tinyguard.repository.GrowthRecordRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.Period;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GrowthRecordService {
    
    private final GrowthRecordRepository growthRecordRepository;
    private final ChildRepository childRepository;
    private final ModelMapper modelMapper;
    
    public GrowthRecordDTO createRecord(GrowthRecordDTO dto) {
        Child child = childRepository.findById(dto.getChildId())
                .orElseThrow(() -> new RuntimeException("Child not found"));
        
        GrowthRecord record = new GrowthRecord();
        record.setChild(child);
        record.setRecordDate(dto.getRecordDate());
        record.setWeight(dto.getWeight());
        record.setHeight(dto.getHeight());
        
        // Calculate age in months
        Period period = Period.between(child.getDateOfBirth(), dto.getRecordDate());
        int ageInMonths = period.getYears() * 12 + period.getMonths();
        record.setAgeInMonths(ageInMonths);
        
        GrowthRecord saved = growthRecordRepository.save(record);
        return mapToDTO(saved);
    }
    
    public List<GrowthRecordDTO> getRecordsByChild(Long childId) {
        return growthRecordRepository.findByChildIdOrderByRecordDateAsc(childId).stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }
    
    public void deleteRecord(Long id) {
        growthRecordRepository.deleteById(id);
    }
    
    private GrowthRecordDTO mapToDTO(GrowthRecord record) {
        GrowthRecordDTO dto = modelMapper.map(record, GrowthRecordDTO.class);
        dto.setChildId(record.getChild().getId());
        return dto;
    }
}
