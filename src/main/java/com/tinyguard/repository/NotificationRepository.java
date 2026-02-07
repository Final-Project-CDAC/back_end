package com.tinyguard.repository;

import com.tinyguard.entity.Notification;
import com.tinyguard.entity.User;
import com.tinyguard.entity.VaccinationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findByUserIdOrderByCreatedAtDesc(Long userId);
    List<Notification> findByUser_IdAndIsReadFalseOrderByCreatedAtDesc(Long userId);
    boolean existsByUserAndTypeAndVaccinationRecord(User user, String type, VaccinationRecord vaccinationRecord);
}
