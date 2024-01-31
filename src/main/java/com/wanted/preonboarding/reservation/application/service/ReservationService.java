package com.wanted.preonboarding.reservation.application.service;

import com.wanted.preonboarding.common.model.PerformanceId;
import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.reservation.application.dto.ReservationResponse;
import com.wanted.preonboarding.reservation.application.exception.NotReservedYet;
import com.wanted.preonboarding.reservation.application.exception.ReservationAlreadyExists;
import com.wanted.preonboarding.reservation.application.exception.ReservationNotFound;
import com.wanted.preonboarding.reservation.domain.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import com.wanted.preonboarding.reservation.domain.event.CheckWaitingEvent;
import com.wanted.preonboarding.reservation.domain.event.ReservationCanceledEvent;
import com.wanted.preonboarding.reservation.domain.event.SeatReservedEvent;
import com.wanted.preonboarding.reservation.domain.event.ValidateReservationRequestEvent;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import com.wanted.preonboarding.reservation.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public ReservationResponse reservePerformance(final ReservationRequest reservationRequest) {
        eventPublisher.publishEvent(ValidateReservationRequestEvent.from(reservationRequest));
        Reservation reservation = Reservation.from(reservationRequest);
        SeatInfo seatInfo = reservation.getSeatInfo();

        validateReservationExistence(reservation.getPerformanceId(), seatInfo);
        eventPublisher.publishEvent(SeatReservedEvent.of(seatInfo, reservationRequest.getPerformanceId()));
        reservation = reservationRepository.save(reservation);
        eventPublisher.publishEvent(CheckWaitingEvent.from(reservation));

        return reservationRepository.findReservationResponseById(reservation.getId())
                .orElseThrow(ReservationNotFound::new);
    }

    @Transactional(readOnly = true)
    public List<ReservationResponse> findReservationsByUserInfo(final UserInfo userInfo) {
        List<ReservationResponse> responses =  reservationRepository.findReservationResponseByUserInfo(userInfo);

        if(responses.isEmpty()) {
            throw new NotReservedYet();
        }

        return responses;
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void cancelReservation(final int reservationId) {
        Reservation reservation = reservationRepository.findById(reservationId)
                .orElseThrow(ReservationNotFound::new);

        eventPublisher.publishEvent(ReservationCanceledEvent.of(reservation.getSeatInfo(), reservation.getPerformanceId()));
        reservationRepository.delete(reservation);
    }

    private void validateReservationExistence(final PerformanceId performanceId, final SeatInfo seatInfo) {
        if(reservationRepository.existsByPerformanceIdAndSeatInfo(performanceId, seatInfo)) {
            throw new ReservationAlreadyExists();
        }
    }
}
