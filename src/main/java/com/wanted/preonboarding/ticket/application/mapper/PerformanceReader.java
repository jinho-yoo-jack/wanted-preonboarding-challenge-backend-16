package com.wanted.preonboarding.ticket.application.mapper;

import com.wanted.preonboarding.ticket.application.dto.response.PerformanceResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Component
public class PerformanceReader {

    private static final String ERROR_MESSAGE_FORMAT = "[%s] -> 공연을 찾을 수 없습니다.";
    private final PerformanceRepository performanceRepository;

    public Performance findById(UUID performanceId) {
        Performance performance = performanceRepository.findById(performanceId)
                .orElseThrow(() -> new EntityNotFoundException(String.format(ERROR_MESSAGE_FORMAT, performanceId)));

        return performance;
    }
}
