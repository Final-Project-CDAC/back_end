package com.tinyguard.repository;

import com.tinyguard.entity.Appointment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AppointmentRepository extends JpaRepository<Appointment, Long> {
    List<Appointment> findByChildId(Long childId);
    List<Appointment> findByChildIdAndStatus(Long childId, Appointment.Status status);
}
