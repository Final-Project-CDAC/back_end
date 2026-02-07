package com.tinyguard.repository;

import com.tinyguard.entity.VaccinationRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface VaccinationRecordRepository extends JpaRepository<VaccinationRecord, Long> {
    List<VaccinationRecord> findByChildId(Long childId);
    List<VaccinationRecord> findByChildIdAndStatus(Long childId, VaccinationRecord.Status status);
    List<VaccinationRecord> findByStatusNot(VaccinationRecord.Status status);
    long countByStatus(VaccinationRecord.Status status);
}
