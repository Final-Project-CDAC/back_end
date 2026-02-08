package com.tinyguard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "appointments")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Appointment {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "child_id", nullable = false)
    private Child child;
    
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor;
    
    @NotNull
    @Column(nullable = false)
    private LocalDate appointmentDate;
    
    @NotNull
    @Column(nullable = false)
    private LocalTime appointmentTime;
    
    @Size(max = 500)
    private String notes;
    
    @Size(max = 1000)
    private String summary;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Status status = Status.PENDING;
    
    public enum Status {
        PENDING, SCHEDULED, COMPLETED, CANCELLED
    }
}
