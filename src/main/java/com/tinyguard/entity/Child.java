package com.tinyguard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "children")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Child {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Size(min = 2, max = 100)
    @Column(nullable = false)
    private String fullName;
    
    @NotNull
    @Column(nullable = false)
    private LocalDate dateOfBirth;
    
    @NotNull
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Gender gender;
    
    @Size(max = 10)
    private String bloodType;
    
    @Size(max = 500)
    private String allergies;
    
    @Size(max = 100)
    private String primaryContactName;
    
    @Size(max = 50)
    private String primaryContactRelation;
    
    @Size(max = 20)
    private String primaryContactPhone;
    
    @Email
    @Size(max = 100)
    private String primaryContactEmail;
    
    @Size(max = 100)
    private String secondaryContactName;
    
    @Size(max = 50)
    private String secondaryContactRelation;
    
    @Size(max = 20)
    private String secondaryContactPhone;
    
    @ManyToOne
    @JoinColumn(name = "parent_id", nullable = false)
    private User parent;
    
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<VaccinationRecord> vaccinationRecords;
    
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Appointment> appointments;
    
    @OneToMany(mappedBy = "child", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<GrowthRecord> growthRecords;
    
    public Integer getAgeInMonths() {
        if (dateOfBirth == null) return null;
        return (int) java.time.temporal.ChronoUnit.MONTHS.between(dateOfBirth, LocalDate.now());
    }
    
    public enum Gender {
        MALE, FEMALE, OTHER
    }
}
