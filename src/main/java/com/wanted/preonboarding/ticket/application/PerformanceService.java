package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.core.domain.exception.CustomException;
import com.wanted.preonboarding.ticket.application.dto.PerformanceDetailDto;
import com.wanted.preonboarding.ticket.application.dto.PerformanceDto;
import com.wanted.preonboarding.ticket.application.dto.PerformanceSearchCondition;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.exception.TicketErrorCode;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    @Transactional(readOnly = true)
    public Page<PerformanceDto> getPerformances(final PerformanceSearchCondition condition, final Pageable pageable) {
        return performanceRepository.searchPage(condition, pageable);
    }

    @Transactional(readOnly = true)
    public PerformanceDetailDto getPerformance(final UUID id) {
        Performance performance =  performanceRepository.findById(id).orElseThrow(() -> new CustomException(TicketErrorCode.PERFORMANCE_NOT_FOUND));
        return PerformanceDetailDto.of(performance);
    }
}
