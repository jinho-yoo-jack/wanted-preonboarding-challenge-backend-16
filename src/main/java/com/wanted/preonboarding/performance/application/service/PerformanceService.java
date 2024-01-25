package com.wanted.preonboarding.performance.application.service;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.domain.repository.PerformanceRepository;
import com.wanted.preonboarding.performanceSeat.domain.event.SeatSoldOutEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceService {

    private final PerformanceRepository performanceRepository;

    @EventListener(SeatSoldOutEvent.class)
    public void updatePerformanceDisabled(final SeatSoldOutEvent seatSoldOutEvent) {
        // 공연의 예약 가능 여부 확인 및 설정
        Performance performance = performanceRepository.findById(seatSoldOutEvent.getPerformanceId())
                .orElseThrow(Error::new);

        performance.disableReservation();
    }
}
