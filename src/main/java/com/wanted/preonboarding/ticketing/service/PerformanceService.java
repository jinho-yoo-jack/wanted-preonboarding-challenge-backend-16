package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.domain.dto.response.ReadPerformanceResponse;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    public Page<ReadPerformanceResponse> read(String isReserve, Pageable pageable) {
        Page<Performance> performances = performanceRepository.findByIsReserve(isReserve, pageable);

        return performances.map(Performance::toReadPerformanceResponse);
    }
}
