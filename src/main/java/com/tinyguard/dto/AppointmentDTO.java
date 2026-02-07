package com.tinyguard.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AppointmentDTO {
    private Long id;
    
    @NotNull
    private Long childId;
    
    private String childName;
    
    private Long doctorId;
    
    private String doctorName;
    
    @NotNull
    private LocalDate appointmentDate;
    
    @NotNull
    private LocalTime appointmentTime;
    
    private String notes;
    private String summary;
    
    @NotBlank
    private String status;
}
