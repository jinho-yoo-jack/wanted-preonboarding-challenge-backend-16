package com.wanted.preonboarding.ticket.infrastructure.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.info.SeatInfo;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {
	Optional<PerformanceSeatInfo> findById(int id);
	Optional<PerformanceSeatInfo> findByPerformanceIdAndRoundAndSeatInfo(UUID performanceId, int round, SeatInfo seatInfo);

	List<PerformanceSeatInfo> findPerformanceSeatInfosByPerformanceIdAndReservedIsFalse(UUID performanceId);
}
