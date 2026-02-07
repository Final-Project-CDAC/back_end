package com.tinyguard.service;

import com.tinyguard.dto.VaccineDTO;
import com.tinyguard.entity.Vaccine;
import com.tinyguard.repository.VaccineRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class VaccineService {
    
    private final VaccineRepository vaccineRepository;
    private final ModelMapper modelMapper;
    
    public VaccineDTO createVaccine(VaccineDTO vaccineDTO) {
        Vaccine vaccine = modelMapper.map(vaccineDTO, Vaccine.class);
        Vaccine saved = vaccineRepository.save(vaccine);
        return modelMapper.map(saved, VaccineDTO.class);
    }
    
    public VaccineDTO updateVaccine(Long id, VaccineDTO vaccineDTO) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));
        
        vaccine.setName(vaccineDTO.getName());
        vaccine.setAgeMilestone(vaccineDTO.getAgeMilestone());
        vaccine.setDoseNumber(vaccineDTO.getDoseNumber());
        vaccine.setDateRange(vaccineDTO.getDateRange());
        vaccine.setDescription(vaccineDTO.getDescription());
        vaccine.setAgeInDays(vaccineDTO.getAgeInDays());
        
        Vaccine updated = vaccineRepository.save(vaccine);
        return modelMapper.map(updated, VaccineDTO.class);
    }
    
    public List<VaccineDTO> getAllVaccines() {
        return vaccineRepository.findAll().stream()
                .map(vaccine -> modelMapper.map(vaccine, VaccineDTO.class))
                .collect(Collectors.toList());
    }
    
    public VaccineDTO getVaccineById(Long id) {
        Vaccine vaccine = vaccineRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vaccine not found"));
        return modelMapper.map(vaccine, VaccineDTO.class);
    }
    
    public void deleteVaccine(Long id) {
        vaccineRepository.deleteById(id);
    }
}
