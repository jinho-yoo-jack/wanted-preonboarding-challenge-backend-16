package com.wanted.preonboarding.reservation.application.service;

import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.infrasturcture.repository.PerformanceRepository;
import com.wanted.preonboarding.reservation.application.dto.ReservationResponse;
import com.wanted.preonboarding.reservation.domain.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import com.wanted.preonboarding.reservation.domain.event.SeatReservedEvent;
import com.wanted.preonboarding.reservation.infrastructure.repository.ReservationRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public ReservationResponse reservePerformance(final ReservationRequest reservationRequest) {
        Performance performance = performanceRepository.findById(reservationRequest.getPerformanceId())
                .orElseThrow(Error::new);
        Reservation reservation = Reservation.from(reservationRequest, performance);
        SeatInfo seatInfo = reservation.getSeatInfo();

        validateReservationExistence(performance, seatInfo);
        reservationRepository.save(reservation);
        eventPublisher.publishEvent(SeatReservedEvent.of(seatInfo, reservationRequest.getPerformanceId()));

        return ReservationResponse.from(performance, reservation);
    }

    private void validateReservationExistence(final Performance performance, final SeatInfo seatInfo) {
        if(reservationRepository.existsByPerformanceAndSeatInfo(performance, seatInfo)) {
            throw new Error("이미 예약된 좌석입니다.");
        }
    }
}
