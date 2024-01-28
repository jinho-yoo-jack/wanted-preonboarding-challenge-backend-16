package com.wanted.preonboarding.ticket.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.service.dto.request.PerformanceCheckRequestDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    @Transactional(readOnly = true)
    public List<PerformanceCheckResponseDto> getPerformances(final PerformanceCheckRequestDto request) {
        log.info("getPerformances request: {}", request);
        final List<Performance> reservablePerformances = performanceRepository.findAllByStatus(request.status());
        return reservablePerformances.stream()
                .map(PerformanceCheckResponseDto::of)
                .toList();
    }
}
