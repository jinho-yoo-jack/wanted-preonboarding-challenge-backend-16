package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.aop.advice.exception.DefaultException;
import com.wanted.preonboarding.ticketing.aop.advice.exception.NotFoundPerformanceException;
import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import com.wanted.preonboarding.ticketing.domain.dto.response.ReadPerformanceResponse;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.repository.PerformanceRepository;
import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerformanceService {
    private static final String POSSIBLE_RESERVATION = "enable";

    private final PerformanceRepository performanceRepository;

    public Page<ReadPerformanceResponse> read(String isReserve, Pageable pageable) {
        if (!isReserve.equals(POSSIBLE_RESERVATION)) {
            throw new DefaultException(ErrorCode.NOT_VALIDATED_PARAM);
        }

        Page<Performance> performances = performanceRepository.findByIsReserve(isReserve, pageable);

        return performances.map(Performance::toReadPerformanceResponse);
    }

    public Performance findPerformance(@NotNull UUID performanceId) {
        return performanceRepository.findById(performanceId)
                .orElseThrow(() -> new NotFoundPerformanceException(ErrorCode.NOT_FOUND_PERFORMANCE));
    }
}
