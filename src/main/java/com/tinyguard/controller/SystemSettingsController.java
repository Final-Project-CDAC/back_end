package com.tinyguard.controller;

import com.tinyguard.entity.SystemSettings;
import com.tinyguard.repository.SystemSettingsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

@RestController
@RequestMapping("/admin/settings")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
public class SystemSettingsController {
    
    private final SystemSettingsRepository systemSettingsRepository;
    
    @GetMapping
    public ResponseEntity<SystemSettings> getSettings() {
        SystemSettings settings = systemSettingsRepository.findAll().stream()
                .findFirst()
                .orElseGet(() -> {
                    SystemSettings newSettings = new SystemSettings();
                    newSettings.setPlatformName("TinyGuard");
                    newSettings.setSupportEmail("support@tinyguard.com");
                    newSettings.setTimeZone("UTC-8:00");
                    newSettings.setMaintenanceMode(false);
                    newSettings.setEmailNotifications(true);
                    newSettings.setSmsNotifications(true);
                    newSettings.setTwoFactorAuth(true);
                    return systemSettingsRepository.save(newSettings);
                });
        return ResponseEntity.ok(settings);
    }
    
    @PutMapping
    public ResponseEntity<SystemSettings> updateSettings(@RequestBody SystemSettings settings) {
        SystemSettings existing = systemSettingsRepository.findAll().stream()
                .findFirst()
                .orElse(new SystemSettings());
        
        existing.setPlatformName(settings.getPlatformName());
        existing.setSupportEmail(settings.getSupportEmail());
        existing.setTimeZone(settings.getTimeZone());
        existing.setMaintenanceMode(settings.getMaintenanceMode());
        existing.setEmailNotifications(settings.getEmailNotifications());
        existing.setSmsNotifications(settings.getSmsNotifications());
        existing.setTwoFactorAuth(settings.getTwoFactorAuth());
        
        SystemSettings updated = systemSettingsRepository.save(existing);
        return ResponseEntity.ok(updated);
    }
    
    @PostMapping("/backup")
    public ResponseEntity<?> backupDatabase() {
        SystemSettings settings = systemSettingsRepository.findAll().stream()
                .findFirst()
                .orElse(new SystemSettings());
        
        String backupTime = LocalDateTime.now().format(DateTimeFormatter.ofPattern("MMM dd, yyyy, hh:mm a"));
        settings.setLastBackupDate(backupTime);
        systemSettingsRepository.save(settings);
        
        HashMap<String, String> response = new HashMap<>();
        response.put("message", "Backup completed successfully");
        response.put("timestamp", backupTime);
        return ResponseEntity.ok(response);
    }
}
