package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.ticket.domain.dto.IsReserveType;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    @Transactional(readOnly = true)
    public List<PerformanceInfo> getAllPerformance() {
        return performanceRepository.findAll()
                .stream()
                .map(PerformanceInfo::of)
                .toList();
    }

    @Transactional(readOnly = true)
    public List<PerformanceInfo> getAblePerformance() {
        IsReserveType isReserveType = IsReserveType.ENABLE;
        return performanceRepository.findByIsReserve(isReserveType.getText())
                .stream()
                .map(PerformanceInfo::of)
                .toList();
    }
}
