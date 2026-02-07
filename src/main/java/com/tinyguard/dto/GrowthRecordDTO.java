package com.tinyguard.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GrowthRecordDTO {
    private Long id;
    
    @NotNull
    private Long childId;
    
    @NotNull
    private LocalDate recordDate;
    
    @NotNull
    private Double weight;
    
    private Double height;
    
    private Integer ageInMonths;
}
