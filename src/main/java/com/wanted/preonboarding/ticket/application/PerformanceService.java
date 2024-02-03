package com.wanted.preonboarding.ticket.application;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.interfaces.dto.PerformanceResponse;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
@RequiredArgsConstructor
public class PerformanceService {
	private final PerformanceRepository performanceRepository;
	private long totalAmount = 0L;

	public List<PerformanceInfo> allPerformanceInfoList() {
		return performanceRepository.findPerformancesByReservedIsFalse()
			.stream()
			.map(PerformanceInfo::of)
			.toList();
	}

	public PerformanceResponse performanceInfoDetail(UUID performanceId) {
		Optional<Performance> performance = performanceRepository.findPerformanceByPerformanceId(performanceId);
		return PerformanceResponse.builder()
			.id(performance.get().getPerformanceId())
			.name(performance.get().getPeformanceName())
			.startDate(performance.get().getStart_date())
			.reserved(performance.get().isReserved())
			.build();
	}
}

