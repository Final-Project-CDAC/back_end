package com.tinyguard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "vaccines")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Vaccine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String name;
    
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String ageMilestone;
    
    @NotBlank
    @Size(max = 50)
    @Column(nullable = false)
    private String doseNumber;
    
    @NotBlank
    @Size(max = 100)
    @Column(nullable = false)
    private String dateRange;
    
    @Size(max = 500)
    private String description;
    
    @Column(nullable = false)
    private Integer ageInDays;
}
