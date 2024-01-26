package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.application.dto.response.PerformanceResponse;
import com.wanted.preonboarding.ticket.application.mapper.PerformanceReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceReader performanceReader;

    public PerformanceResponse findOne(UUID performanceId) {
        return performanceReader.findById(performanceId);
    }
}
