package com.wanted.preonboarding.performanceSeat.application.service;

import com.wanted.preonboarding.performanceSeat.application.exception.PerformanceSeatAlreadyReserved;
import com.wanted.preonboarding.performanceSeat.application.exception.PerformanceSeatInfoNotFound;
import com.wanted.preonboarding.performanceSeat.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.performanceSeat.domain.event.SeatSoldOutEvent;
import com.wanted.preonboarding.performanceSeat.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.reservation.domain.event.SeatReservedEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceSeatService {

    private static final String RESERVABLE = "enable";

    private final ApplicationEventPublisher eventPublisher;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @Transactional
    @EventListener(SeatReservedEvent.class)
    public void reserveSeat(final SeatReservedEvent seatReservedEvent) {
        PerformanceSeatInfo performanceSeatInfo = findSeatBySeatReservedEvent(seatReservedEvent);

        if(performanceSeatInfo.isReserved()) {
            throw new PerformanceSeatAlreadyReserved();
        }
        performanceSeatInfo.disableReservation();

        if(!performanceSeatInfoRepository.existsByIsReserveAndPerformanceId(RESERVABLE, seatReservedEvent.getPerformanceId())) {
            eventPublisher.publishEvent(SeatSoldOutEvent.of(seatReservedEvent.getPerformanceId()));
        }
    }

    private PerformanceSeatInfo findSeatBySeatReservedEvent(final SeatReservedEvent seatReservedEvent) {
        return performanceSeatInfoRepository
                .findBySeatInfoAndPerformanceId(seatReservedEvent.getSeatInfo(),
                        seatReservedEvent.getPerformanceId())
                .orElseThrow(PerformanceSeatInfoNotFound::new);
    }
}
