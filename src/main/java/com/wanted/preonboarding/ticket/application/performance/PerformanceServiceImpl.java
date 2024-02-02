package com.wanted.preonboarding.ticket.application.performance;

import com.wanted.preonboarding.ticket.domain.performance.Performance;
import com.wanted.preonboarding.ticket.domain.performance.PerformanceRepository;
import com.wanted.preonboarding.ticket.domain.performance.model.ReserveState;
import com.wanted.preonboarding.ticket.dto.response.page.PageResponse;
import com.wanted.preonboarding.ticket.dto.response.performance.PerformanceInfo;
import com.wanted.preonboarding.ticket.exception.notfound.PerformanceNotFoundException;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Transactional(readOnly = true)
@Service
public class PerformanceServiceImpl implements PerformanceService {

    private final PerformanceRepository performanceRepository;

    @Override
    public PerformanceInfo findPerformance(final UUID performanceId) {
        Performance performance = performanceRepository.findById(performanceId)
            .orElseThrow(PerformanceNotFoundException::new);
        return PerformanceInfo.of(performance);
    }

    @Override
    public PageResponse<PerformanceInfo> findPerformanceAll(final String reserveState, final Pageable pageable) {
        Page<Performance> performances = performanceRepository.findAll(reserveState, pageable);
        List<PerformanceInfo> performanceInfos = performances.getContent().stream()
            .map(PerformanceInfo::of)
            .toList();

        return PageResponse.of(performances, performanceInfos);
    }
}
