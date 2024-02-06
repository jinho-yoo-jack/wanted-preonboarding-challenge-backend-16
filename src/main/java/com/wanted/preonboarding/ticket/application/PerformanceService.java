package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.application.dto.AwaitInfo;
import com.wanted.preonboarding.ticket.application.dto.response.PerformanceResponse;
import com.wanted.preonboarding.ticket.application.mapper.PerformanceReader;
import com.wanted.preonboarding.ticket.application.validator.AwaitValidator;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.infrastructure.repository.AwaitRepository;
import com.wanted.preonboarding.user.User;
import com.wanted.preonboarding.user.application.mapper.UserReader;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceReader performanceReader;
    private final UserReader userReader;
    private final AwaitRepository awaitRepository;

    private final AwaitValidator awaitValidator;

    public PerformanceResponse findOne(UUID performanceId) {
        Performance performance = performanceReader.findById(performanceId);
        return PerformanceResponse.from(performance);
    }

    public List<PerformanceResponse> findPerformances(String isReserved) {
        List<Performance> performances = performanceReader.findByIsReserve(isReserved);

        return performances.stream()
                .map(PerformanceResponse::from)
                .toList();
    }

    @Transactional
    public void await(UUID performanceId, Long userId) {
        awaitValidator.validExistsPerformance(performanceId);
        User user = userReader.findById(userId);

        AwaitInfo awaitInfo = AwaitInfo.of(performanceId, user);
        awaitRepository.save(awaitInfo);
    }
}
