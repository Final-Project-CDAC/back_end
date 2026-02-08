package com.tinyguard.dto;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DashboardStatsDTO {
    private Long totalParents;
    private Long totalChildren;
    private Long totalVaccinationRecords;
    private Long completedVaccinations;
}
