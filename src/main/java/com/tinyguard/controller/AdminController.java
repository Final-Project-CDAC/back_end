package com.tinyguard.controller;

import com.tinyguard.dto.DashboardStatsDTO;
import com.tinyguard.entity.ActivityLog;
import com.tinyguard.entity.User;
import com.tinyguard.repository.*;
import com.tinyguard.service.ActivityLogService;
import com.tinyguard.service.NotificationSchedulerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/admin")
@RequiredArgsConstructor
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"}, allowCredentials = "true")
public class AdminController {
    
    private final UserRepository userRepository;
    private final ChildRepository childRepository;
    private final VaccinationRecordRepository vaccinationRecordRepository;
    private final ActivityLogService activityLogService;
    private final NotificationSchedulerService notificationSchedulerService;
    
    @GetMapping("/dashboard/stats")
    public ResponseEntity<DashboardStatsDTO> getDashboardStats() {
        long totalParents = userRepository.countByRole(User.Role.PARENT);
        long totalChildren = childRepository.count();
        long totalVaccinationRecords = vaccinationRecordRepository.count();
        long completedVaccinations = vaccinationRecordRepository.countByStatus(com.tinyguard.entity.VaccinationRecord.Status.COMPLETED);
        
        DashboardStatsDTO stats = new DashboardStatsDTO(
            totalParents,
            totalChildren,
            totalVaccinationRecords,
            completedVaccinations
        );
        
        return ResponseEntity.ok(stats);
    }
    
    @GetMapping("/activities")
    public ResponseEntity<List<ActivityLog>> getRecentActivities() {
        return ResponseEntity.ok(activityLogService.getRecentActivities());
    }
    
    @PostMapping("/notifications/generate-reminders")
    public ResponseEntity<Map<String, String>> generateNotifications() {
        notificationSchedulerService.generateVaccinationReminders();
        return ResponseEntity.ok(Map.of("message", "Notifications generated successfully"));
    }
}
