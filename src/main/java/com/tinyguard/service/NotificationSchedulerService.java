package com.tinyguard.service;

import com.tinyguard.entity.*;
import com.tinyguard.repository.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationSchedulerService {
    
    private final VaccinationRecordRepository vaccinationRecordRepository;
    private final NotificationRepository notificationRepository;
    private final ChildRepository childRepository;
    
    // Run daily at 9 AM
    @Scheduled(cron = "0 0 9 * * *")
    public void generateVaccinationReminders() {
        log.info("Starting vaccination reminder generation...");
        LocalDate today = LocalDate.now();
        
        List<VaccinationRecord> pendingRecords = vaccinationRecordRepository
            .findByStatusNot(VaccinationRecord.Status.COMPLETED);
        
        log.info("Found {} pending vaccination records", pendingRecords.size());
        
        List<Notification> notificationsToSave = new java.util.ArrayList<>();
        
        for (VaccinationRecord record : pendingRecords) {
            LocalDate dueDate = record.getDueDate();
            long daysUntilDue = ChronoUnit.DAYS.between(today, dueDate);
            
            log.debug("Processing record: vaccine={}, child={}, dueDate={}, daysUntilDue={}",
                record.getVaccine().getName(),
                record.getChild().getFullName(),
                dueDate,
                daysUntilDue);
            
            String notificationType = null;
            String message = null;
            String title = "Vaccination Reminder";
            
            if (daysUntilDue == 7) {
                notificationType = "VACCINATION_REMINDER_7_DAYS";
                message = String.format("Reminder: %s vaccination for %s is due in 1 week (on %s). Please schedule an appointment.",
                    record.getVaccine().getName(),
                    record.getChild().getFullName(),
                    dueDate);
            } else if (daysUntilDue == 3) {
                notificationType = "VACCINATION_REMINDER_3_DAYS";
                message = String.format("Important: %s vaccination for %s is due in 3 days (on %s). Please ensure appointment is scheduled.",
                    record.getVaccine().getName(),
                    record.getChild().getFullName(),
                    dueDate);
            } else if (daysUntilDue == 1) {
                notificationType = "VACCINATION_REMINDER_1_DAY";
                message = String.format("Urgent: %s vaccination for %s is due tomorrow (%s). Don't miss this important vaccination!",
                    record.getVaccine().getName(),
                    record.getChild().getFullName(),
                    dueDate);
            } else if (daysUntilDue == 0) {
                notificationType = "VACCINATION_DUE_TODAY";
                title = "Vaccination Due Today";
                message = String.format("Today: %s vaccination for %s is due today! Please visit the doctor.",
                    record.getVaccine().getName(),
                    record.getChild().getFullName());
            } else if (daysUntilDue < 0) {
                long daysOverdue = Math.abs(daysUntilDue);
                notificationType = "VACCINATION_OVERDUE";
                title = "Overdue Vaccination";
                message = String.format("Overdue: %s vaccination for %s was due %d days ago (on %s). Please schedule immediately!",
                    record.getVaccine().getName(),
                    record.getChild().getFullName(),
                    daysOverdue,
                    dueDate);
            }
            
            if (notificationType != null && message != null) {
                boolean exists = notificationRepository.existsByUserAndTypeAndVaccinationRecord(
                    record.getChild().getParent(), 
                    notificationType, 
                    record
                );
                
                log.debug("Notification type: {}, exists: {}", notificationType, exists);
                
                if (!exists) {
                    Notification notification = new Notification();
                    notification.setUser(record.getChild().getParent());
                    notification.setType(notificationType);
                    notification.setTitle(title);
                    notification.setMessage(message);
                    notification.setVaccinationRecord(record);
                    notification.setIsRead(false);
                    notificationsToSave.add(notification);
                    log.info("Created notification: {} for user {}", notificationType, record.getChild().getParent().getEmail());
                }
            }
        }
        
        if (!notificationsToSave.isEmpty()) {
            notificationRepository.saveAll(notificationsToSave);
            log.info("Saved {} notifications", notificationsToSave.size());
        } else {
            log.info("No new notifications to create");
        }
    }
}
