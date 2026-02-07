package com.tinyguard.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class VaccineDTO {
    private Long id;
    
    @NotBlank
    private String name;
    
    @NotBlank
    private String ageMilestone;
    
    @NotBlank
    private String doseNumber;
    
    @NotBlank
    private String dateRange;
    
    private String description;
    
    @NotNull
    private Integer ageInDays;
}
