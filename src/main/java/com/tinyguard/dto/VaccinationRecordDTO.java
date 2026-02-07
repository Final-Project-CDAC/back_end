package com.tinyguard.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationRecordDTO {
    private Long id;
    
    @NotNull
    private Long childId;
    
    private String childName;
    private Integer childAge;
    private String parentName;
    private String parentEmail;
    
    @NotNull
    private Long vaccineId;
    
    @NotNull
    private LocalDate dueDate;
    
    private LocalDate givenDate;
    
    @NotBlank
    private String status;
    
    private String notes;
    private String vaccineName;
    private String ageMilestone;
}
