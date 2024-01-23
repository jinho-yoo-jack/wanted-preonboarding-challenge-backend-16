package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticketing.domain.entity.Reservation;
import com.wanted.preonboarding.ticketing.domain.dto.request.ReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.response.ReservationResponse;
import com.wanted.preonboarding.ticketing.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticketing.repository.ReservationRepository;
import com.wanted.preonboarding.ticketing.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @Transactional
    public ReservationResponse createReservation(ReservationRequest reservationRequest) {
        Reservation reservation = reserveTicket(reservationRequest);
        Performance performance = findPerformanceName(reservationRequest);
        reserveSeat(reservationRequest);

        return reservation.toCreateReservationResponse(performance);
    }

    private Performance findPerformanceName(ReservationRequest reservationRequest) {
        return performanceRepository.getReferenceById(reservationRequest.getPerformanceId());
    }

    private void reserveSeat(ReservationRequest reservationRequest) {
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.getReferenceById(reservationRequest.getSeatId());
        performanceSeatInfo.updateReservationStatus(reservationRequest);
    }

    private Reservation reserveTicket(ReservationRequest reservationRequest) {
        Reservation reservation = reservationRequest.fromTicket();

        return reservationRepository.save(reservation);
    }
}
