package com.wanted.preonboarding.ticket.infrastructure.store;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PerformanceSeatInfoStore {

    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @Transactional
    public PerformanceSeatInfo store(PerformanceSeatInfo performanceSeatInfo) {
        return performanceSeatInfoRepository.save(performanceSeatInfo);
    }
}
