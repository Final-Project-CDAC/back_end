package com.tinyguard.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "notifications")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Notification {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonIgnoreProperties({"password", "notifications", "children"})
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "vaccination_record_id")
    @JsonIgnoreProperties({"child", "vaccine"})
    private VaccinationRecord vaccinationRecord;
    
    @NotBlank
    @Column(nullable = false)
    private String title;
    
    @NotBlank
    @Column(nullable = false, length = 500)
    private String message;
    
    @NotBlank
    @Column(nullable = false)
    private String type; // VACCINATION_REMINDER_7_DAYS, VACCINATION_REMINDER_3_DAYS, etc.
    
    @Column(nullable = false)
    private Boolean isRead = false;
    
    @Column(nullable = false)
    private Boolean emailSent = false;
    
    @Column(nullable = false)
    private Boolean smsSent = false;
    
    @Column(nullable = false)
    private LocalDateTime createdAt;
    
    @PrePersist
    protected void onCreate() {
        if (createdAt == null) {
            createdAt = LocalDateTime.now();
        }
    }
}
