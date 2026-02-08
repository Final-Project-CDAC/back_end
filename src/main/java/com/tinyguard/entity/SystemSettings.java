package com.tinyguard.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "system_settings")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SystemSettings {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank
    @Column(nullable = false)
    private String platformName;
    
    @NotBlank
    @Email
    @Column(nullable = false)
    private String supportEmail;
    
    @NotBlank
    @Column(nullable = false)
    private String timeZone;
    
    @Column(nullable = false)
    private Boolean maintenanceMode = false;
    
    @Column(nullable = false)
    private Boolean emailNotifications = true;
    
    @Column(nullable = false)
    private Boolean smsNotifications = true;
    
    @Column(nullable = false)
    private Boolean twoFactorAuth = true;
    
    private String lastBackupDate;
    
    @PrePersist
    protected void onCreate() {
        if (platformName == null) {
            platformName = "TinyGuard";
        }
        if (timeZone == null) {
            timeZone = "UTC-8:00";
        }
    }
}
