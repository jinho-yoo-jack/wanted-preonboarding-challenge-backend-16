package com.wanted.preonboarding.ticket.application.ticket;

import com.wanted.preonboarding.ticket.domain.dto.request.AddPerformance;
import com.wanted.preonboarding.ticket.domain.dto.assembler.PerformanceAssembler;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceInfoResponse;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
@Service
@RequiredArgsConstructor
public class AdminService {
    private final PerformanceRepository performanceRepository;
    private final PerformanceAssembler performanceAssembler;

    @Transactional
    public PerformanceInfoResponse addPerformance(AddPerformance addPerformance)
    {
        return performanceAssembler.toDto(
                performanceRepository.save(performanceAssembler.toPerformance(addPerformance))
        );
    }
}
