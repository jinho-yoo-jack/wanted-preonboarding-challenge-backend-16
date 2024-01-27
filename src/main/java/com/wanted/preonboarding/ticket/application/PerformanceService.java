package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.application.dto.response.PerformanceResponse;
import com.wanted.preonboarding.ticket.application.mapper.PerformanceReader;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceReader performanceReader;

    public PerformanceResponse findOne(UUID performanceId) {
        Performance performance = performanceReader.findById(performanceId);
        return PerformanceResponse.from(performance);
    }

    public List<PerformanceResponse> findAvailablePerformances(String isReserved) {
        throw new UnsupportedOperationException();
    }
}
