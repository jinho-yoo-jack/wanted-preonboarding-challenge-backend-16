package com.wanted.preonboarding.reservation.application;

import com.wanted.preonboarding.reservation.domain.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import com.wanted.preonboarding.reservation.domain.event.SeatReservedEvent;
import com.wanted.preonboarding.reservation.domain.repository.ReservationRepository;
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
    private final ApplicationEventPublisher eventPublisher;

    @Transactional
    public void reservePerformance(final ReservationRequest reservationRequest) {
        Reservation reservation = Reservation.from(reservationRequest);

        if(reservationRepository.existsByPerformanceIdAndSeatInfo(reservation.getPerformanceId(),
                reservation.getSeatInfo())) {
            throw new Error("이미 예약된 좌석입니다.");
        }

        reservationRepository.save(reservation);
        eventPublisher.publishEvent(SeatReservedEvent.of(reservation.getSeatInfo(), reservation.getPerformanceId()));
    }
}
