package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceFindResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.exception.ErrorCode;
import com.wanted.preonboarding.ticket.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PerformanceService {
    private final PerformanceRepository performanceRepository;

    public List<PerformanceFindResponse> findPerformances(String isReserve) {
        List<Performance> performanceList = findPerformanceByIsReverse(isReserve);
        checkPerformanceEmpty(performanceList);
        return getPerformanceFindResponseList(performanceList);

    }

    private static List<PerformanceFindResponse> getPerformanceFindResponseList(List<Performance> performanceList) {
        return performanceList.stream()
                .map(Performance::toPerformanceFindResponse)
                .collect(Collectors.toList());
    }

    private static void checkPerformanceEmpty(List<Performance> performanceList) {
        if (performanceList.isEmpty()) {
            throw new PerformanceNotFoundException(ErrorCode.PERFORMANCE_NOT_FOUND);
        }
    }

    private List<Performance> findPerformanceByIsReverse(String isReserve) {
        return performanceRepository.findByIsReserve(isReserve);
    }
}
