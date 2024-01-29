package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceRequest;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceAdminService {

	private final PerformanceRepository performanceRepository;


	@Transactional
	public UUID register(PerformanceRequest request) {
		Performance entity = Performance.builder()
			.name(request.name())
			.price(request.price())
			.round(request.round())
			.type(request.type())
			.reserve(request.isReserve())
			.start_date(request.startDate())
			.build();

		return performanceRepository.save(entity).getId();
	}
}
