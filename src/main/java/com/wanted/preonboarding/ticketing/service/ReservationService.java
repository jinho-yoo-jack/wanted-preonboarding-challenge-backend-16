package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.domain.dto.request.ReadReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.response.ReadPerformanceResponse;
import com.wanted.preonboarding.ticketing.domain.dto.response.ReadReservationResponse;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticketing.domain.entity.Reservation;
import com.wanted.preonboarding.ticketing.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.response.CreateReservationResponse;
import com.wanted.preonboarding.ticketing.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticketing.repository.ReservationRepository;
import com.wanted.preonboarding.ticketing.repository.PerformanceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @Transactional
    public CreateReservationResponse createReservation(CreateReservationRequest createReservationRequest) {
        Performance performance = findPerformanceName(createReservationRequest);
        Reservation reservation = reserveTicket(createReservationRequest, performance);
        int changes = createReservationRequest.calculateChange(performance);
        reserveSeat(createReservationRequest);

        return reservation.toCreateReservationResponse(performance, changes);
    }

    private Performance findPerformanceName(CreateReservationRequest createReservationRequest) {
        return performanceRepository.getReferenceById(createReservationRequest.getPerformanceId());
    }

    private void reserveSeat(CreateReservationRequest createReservationRequest) {
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.getReferenceById(createReservationRequest.getSeatId());
        performanceSeatInfo.updateReservationStatus(createReservationRequest);
    }

    private Reservation reserveTicket(CreateReservationRequest createReservationRequest, Performance performance) {
        Reservation reservation = createReservationRequest.fromTicket(performance);
        return reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public Page<ReadReservationResponse> readReservation(ReadReservationRequest reservationRequest, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findByPhoneNumberAndName(reservationRequest.getPhoneNumber()
                , reservationRequest.getReservationName()
                , pageable);

        return reservations.map(Reservation::toReadReservationResponse);
    }

    @Transactional(readOnly = true)
    public Page<ReadPerformanceResponse> readPerformance(String isReserve, Pageable pageable) {
        Page<Performance> performances = performanceRepository.findByIsReserve(isReserve, pageable);

        return performances.map(Performance::toReadPerformanceResponse);
    }
}
