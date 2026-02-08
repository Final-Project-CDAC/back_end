package com.tinyguard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "growth_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GrowthRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;
    
    @NotNull
    @Column(nullable = false)
    private LocalDate recordDate;
    
    @NotNull
    @Column(nullable = false)
    private Double weight;
    
    private Double height;
    
    @NotNull
    @Column(nullable = false)
    private Integer ageInMonths;
}
