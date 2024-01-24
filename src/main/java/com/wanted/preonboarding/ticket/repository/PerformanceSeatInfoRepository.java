package com.wanted.preonboarding.ticket.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Long> {

    Optional<PerformanceSeatInfo> findByPerformanceIdAndRoundAndLineAndSeat(UUID performanceId,int round, char line, int seat);
}
