package com.wanted.preonboarding.ticket.infrastructure.reader;

import com.wanted.preonboarding.core.exception.PerformanceSeatInfoNotFoundException;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PerformanceSeatInfoReader {

    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @Transactional(readOnly = true)
    public PerformanceSeatInfo getOpeningSeatInfo(Performance performance, String line, int seat) {
        return performanceSeatInfoRepository.findByPerformanceAndLineAndSeat(performance, line, seat).orElseThrow(PerformanceSeatInfoNotFoundException::new);
    }
}
