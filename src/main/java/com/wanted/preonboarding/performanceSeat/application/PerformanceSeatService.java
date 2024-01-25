package com.wanted.preonboarding.performanceSeat.application;

import com.wanted.preonboarding.common.model.ReservableEntity;
import com.wanted.preonboarding.performanceSeat.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.performanceSeat.domain.event.SeatSoldOutEvent;
import com.wanted.preonboarding.performanceSeat.domain.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.reservation.domain.event.SeatReservedEvent;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceSeatService {

    private static final String RESERVABLE = "enable";

    private final ApplicationEventPublisher eventPublisher;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @EventListener(SeatReservedEvent.class)
    @Transactional
    public void reserveSeat(final SeatReservedEvent seatReservedEvent) {
        PerformanceSeatInfo performanceSeatInfo = findSeatBySeatReservedEvent(seatReservedEvent);

        if(performanceSeatInfo.isReserved()) {
            throw new Error("이미 예약된 좌석입니다.");
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
                .orElseThrow(Error::new);
    }
}
