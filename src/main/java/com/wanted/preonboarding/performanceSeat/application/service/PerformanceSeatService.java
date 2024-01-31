package com.wanted.preonboarding.performanceSeat.application.service;

import com.wanted.preonboarding.common.model.PerformanceId;
import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performanceSeat.application.exception.PerformanceSeatAlreadyReserved;
import com.wanted.preonboarding.performanceSeat.application.exception.PerformanceSeatInfoNotFound;
import com.wanted.preonboarding.performanceSeat.domain.dto.PerformanceSeatResponse;
import com.wanted.preonboarding.performanceSeat.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.performanceSeat.domain.event.EnablePerformanceReservationEvent;
import com.wanted.preonboarding.performanceSeat.domain.event.SeatSoldOutEvent;
import com.wanted.preonboarding.performanceSeat.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.reservation.domain.event.ReservationCanceledEvent;
import com.wanted.preonboarding.reservation.domain.event.SeatReservedEvent;
import com.wanted.preonboarding.reservation.domain.event.ValidateReservationRequestEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class PerformanceSeatService {

    private static final String RESERVABLE = "enable";

    private final ApplicationEventPublisher eventPublisher;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @TransactionalEventListener(SeatReservedEvent.class)
    public void reserveSeat(final SeatReservedEvent seatReservedEvent) {
        PerformanceSeatInfo performanceSeatInfo = findSeatBySeatReservedEvent(seatReservedEvent);
        validateSeatReserveState(performanceSeatInfo);
        performanceSeatInfo.disableReservation();
        if(isPerformanceSeatSoldOut(seatReservedEvent)) {
            eventPublisher.publishEvent(SeatSoldOutEvent.of(seatReservedEvent.getPerformanceId()));
        }
    }

    @TransactionalEventListener(ReservationCanceledEvent.class)
    public void enableSeatReservation(final ReservationCanceledEvent reservationCanceledEvent) {
        PerformanceSeatInfo performanceSeatInfo =
                findSeatByReservationCanceledEvent(reservationCanceledEvent);
        performanceSeatInfo.enableReservation();
        eventPublisher.publishEvent(EnablePerformanceReservationEvent.of(reservationCanceledEvent.getPerformanceId()));
    }

    @TransactionalEventListener(ValidateReservationRequestEvent.class)
    public void validateReservationRequest(final ValidateReservationRequestEvent validateReservationRequestEvent) {
        findSeatBySeatInfoAndPerformanceId(
                validateReservationRequestEvent.getSeatInfo(),
                validateReservationRequestEvent.getPerformanceId()
        );
    }

    @Transactional(readOnly = true)
    public List<PerformanceSeatResponse> findReservableSeats(final UUID performanceId) {
        return performanceSeatInfoRepository.findAllByPerformanceId(PerformanceId.of(performanceId))
                .stream()
                .filter(PerformanceSeatInfo::canReserve)
                .map(PerformanceSeatResponse::from).toList();
    }

    private PerformanceSeatInfo findSeatBySeatReservedEvent(final SeatReservedEvent seatReservedEvent) {
        return findSeatBySeatInfoAndPerformanceId(
                seatReservedEvent.getSeatInfo(),
                seatReservedEvent.getPerformanceId()
        );
    }

    private PerformanceSeatInfo findSeatByReservationCanceledEvent(final ReservationCanceledEvent reservationCanceledEvent) {
        return findSeatBySeatInfoAndPerformanceId(
                reservationCanceledEvent.getSeatInfo(),
                reservationCanceledEvent.getPerformanceIdValue()
        );
    }

    private PerformanceSeatInfo findSeatBySeatInfoAndPerformanceId(final SeatInfo seatInfo, final UUID performanceId) {
        return performanceSeatInfoRepository
                .findBySeatInfoAndPerformanceId(seatInfo,
                        PerformanceId.of(performanceId))
                .orElseThrow(PerformanceSeatInfoNotFound::new);
    }

    private boolean isPerformanceSeatSoldOut(SeatReservedEvent seatReservedEvent) {
        return !performanceSeatInfoRepository.existsByReserveStateAndPerformanceId(
                RESERVABLE,
                PerformanceId.of(seatReservedEvent.getPerformanceId()));
    }

    private void validateSeatReserveState(PerformanceSeatInfo performanceSeatInfo) {
        if(performanceSeatInfo.isReserved()) {
            throw new PerformanceSeatAlreadyReserved();
        }
    }
}
