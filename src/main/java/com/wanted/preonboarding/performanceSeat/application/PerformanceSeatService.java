package com.wanted.preonboarding.performanceSeat.application;

import com.wanted.preonboarding.performanceSeat.domain.event.SeatSoldOutEvent;
import com.wanted.preonboarding.reservation.domain.event.SeatReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceSeatService {

    private final ApplicationEventPublisher eventPublisher;

    @EventListener(SeatReservedEvent.class)
    public void reserveSeat(SeatReservedEvent seatReservedEvent) {
        // 검증 및 데이터 수정 로직 필요
        eventPublisher.publishEvent(SeatSoldOutEvent.of(seatReservedEvent.getPerformanceId()));
    }
}
