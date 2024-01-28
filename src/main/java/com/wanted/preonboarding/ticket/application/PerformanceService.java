package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.exception.NotFoundException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@Transactional
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    public List<Performance> getPerformances() {
        return performanceRepository.findAll();
    }

    public Performance getPerformance(final UUID id) {
        return performanceRepository.findById(id).orElseThrow(() -> new NotFoundException("공연이 존재하지 않습니다."));
    }
}
