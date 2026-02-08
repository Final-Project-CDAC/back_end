package com.tinyguard.repository;

import com.tinyguard.entity.GrowthRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface GrowthRecordRepository extends JpaRepository<GrowthRecord, Long> {
    List<GrowthRecord> findByChildIdOrderByRecordDateAsc(Long childId);
}
