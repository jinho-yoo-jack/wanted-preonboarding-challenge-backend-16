package com.wanted.preonboarding.ticket.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Long> {
	List<PerformanceSeatInfo> findAllByPerformanceId(UUID id);

	List<PerformanceSeatInfo> findAllByPerformanceIdAndIsReserve(UUID id, String status);
}
