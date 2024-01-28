package com.wanted.preonboarding.ticket.application.validator;

import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AwaitValidator {

    public static final String AWAIT_ERROR_MESSAGE_FORMAT = "[%s] 는 존재하지 않는 공연입니다.";

    private final PerformanceRepository performanceRepository;

    public void validExistsPerformance(UUID performanceId) {
        if (performanceRepository.findById(performanceId).isEmpty()) {
            throw new EntityNotFoundException(String.format(AWAIT_ERROR_MESSAGE_FORMAT, performanceId));
        }
    }
}
