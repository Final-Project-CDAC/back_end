package com.tinyguard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;

@Entity
@Table(name = "vaccination_records")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class VaccinationRecord {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;
    
    @ManyToOne
    @JoinColumn(name = "vaccine_id", nullable = false)
    private Vaccine vaccine;
    
    @NotNull
    @Column(nullable = false)
    private LocalDate dueDate;
    
    private LocalDate givenDate;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.UPCOMING;
    
    @Size(max = 500)
    private String notes;
    
    public enum Status {
        UPCOMING, DUE_SOON, OVERDUE, COMPLETED
    }
}
