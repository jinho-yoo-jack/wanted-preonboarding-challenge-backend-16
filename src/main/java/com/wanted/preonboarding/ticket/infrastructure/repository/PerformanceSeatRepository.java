package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PerformanceSeatRepository extends JpaRepository<PerformanceSeat, Integer> {
    Optional<PerformanceSeat> findByPerformanceIdAndRoundAndLineAndSeat(UUID performanceId, int round, char line, int seat);
    List<PerformanceSeat> findByPerformanceIdAndIsReserve(UUID performanceId, String isReserve);
}
