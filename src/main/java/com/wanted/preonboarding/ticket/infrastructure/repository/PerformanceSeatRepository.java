package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PerformanceSeatRepository extends JpaRepository<PerformanceSeatInfo, Long> {

    PerformanceSeatInfo findByPerformanceIdAndRoundAndLineAndSeat(UUID performanceId, int round, char line, int seat);

    boolean existsByPerformanceIdAndIsReserve(UUID performanceId, String isReserve);
}
