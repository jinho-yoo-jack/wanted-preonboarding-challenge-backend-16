package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {
    Optional<PerformanceSeatInfo> findByPerformanceIdAndRoundAndSeatAndLine(UUID performanceId, int round, int seat, char line);
}
