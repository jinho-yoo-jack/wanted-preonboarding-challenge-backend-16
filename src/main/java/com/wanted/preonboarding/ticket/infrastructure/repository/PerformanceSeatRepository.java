package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PerformanceSeatRepository extends JpaRepository<PerformanceSeat, Integer> {
    PerformanceSeat findByPerformanceIdAndRoundAndLineAndSeat(UUID performanceId, Integer round, Character line, Integer seat);
    List<PerformanceSeat> findByPerformanceIdAndIsReserve(UUID performanceId, String isReserve);
}
