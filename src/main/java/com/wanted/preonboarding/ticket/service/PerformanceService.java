package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.ticket.controller.model.PerformanceSelectModel;
import com.wanted.preonboarding.ticket.domain.Performance;
import com.wanted.preonboarding.ticket.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.wanted.preonboarding.core.code.ErrorMessage.EMPTY_DATA;

@Service
@RequiredArgsConstructor
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    @Transactional(readOnly = true)
    public List<PerformanceSelectModel> getPerformances(boolean isReserve) {
        List<Performance> performances = performanceRepository.findAllByIsReserve(isReserve);
        return performances.stream()
                .map(PerformanceSelectModel::from)
                .toList();
    }

    @Transactional(readOnly = true)
    public PerformanceSelectModel getPerformance(Long id) {
        Performance performance = performanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(EMPTY_DATA.getDescription()));
        return PerformanceSelectModel.from(performance);
    }
}
