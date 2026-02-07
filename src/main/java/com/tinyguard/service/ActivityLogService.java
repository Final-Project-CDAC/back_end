package com.tinyguard.service;

import com.tinyguard.entity.ActivityLog;
import com.tinyguard.repository.ActivityLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityLogService {
    
    private final ActivityLogRepository activityLogRepository;
    
    public void logActivity(String activityType, String description, String userEmail) {
        ActivityLog log = new ActivityLog();
        log.setActivityType(activityType);
        log.setDescription(description);
        log.setUserEmail(userEmail);
        activityLogRepository.save(log);
    }
    
    public List<ActivityLog> getRecentActivities() {
        return activityLogRepository.findTop20ByOrderByTimestampDesc();
    }
}
