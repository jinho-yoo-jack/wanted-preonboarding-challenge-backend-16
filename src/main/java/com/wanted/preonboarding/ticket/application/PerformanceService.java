package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceFindResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
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
        List<Performance> performanceList = performanceRepository.findByIsReserve(isReserve);
        if (performanceList.isEmpty()) {
            throw new PerformanceNotFoundException("공연 정보가 존재하지 않습니다.");    //TODO: 분리하기, String 상수로
        } else {
            return performanceList.stream()
                    .map(Performance::toPerformanceFindResponse)
                    .collect(Collectors.toList());
        }
    }
}
