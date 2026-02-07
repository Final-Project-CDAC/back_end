package com.tinyguard.dto;

import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ChildDTO {
    private Long id;
    
    @NotBlank
    @Size(min = 2, max = 100)
    private String fullName;
    
    @NotNull
    private LocalDate dateOfBirth;
    
    @NotBlank
    private String gender;
    
    private String bloodType;
    private String allergies;
    private String primaryContactName;
    private String primaryContactRelation;
    private String primaryContactPhone;
    
    @Email
    private String primaryContactEmail;
    private String secondaryContactName;
    private String secondaryContactRelation;
    private String secondaryContactPhone;
}
