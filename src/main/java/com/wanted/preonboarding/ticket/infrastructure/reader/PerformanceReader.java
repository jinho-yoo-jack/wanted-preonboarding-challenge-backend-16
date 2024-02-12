package com.wanted.preonboarding.ticket.infrastructure.reader;

import com.wanted.preonboarding.core.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.ticket.domain.code.ActiveType;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceId;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerformanceReader {

    private final PerformanceRepository performanceRepository;

    @Transactional(readOnly = true)
    public Performance getOpeningPerformance(PerformanceId performanceId) {
        return performanceRepository.findByIdAndIsReserve(performanceId, ActiveType.OPEN).orElseThrow(PerformanceNotFoundException::new);
    }

    @Transactional(readOnly = true)
    public List<Performance> getOpeningPerformances() {
        return performanceRepository.findAllByIsReserve(ActiveType.OPEN);
    }

    @Transactional(readOnly = true)
    public Performance getById(UUID performanceId) {
        return performanceRepository.findByIdId(performanceId).orElseThrow(PerformanceNotFoundException::new);
    }
}
