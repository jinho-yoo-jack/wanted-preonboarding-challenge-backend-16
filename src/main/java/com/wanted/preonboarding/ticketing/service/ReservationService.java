package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.aop.advice.exception.PerformanceNotFountException;
import com.wanted.preonboarding.ticketing.aop.advice.exception.ReservationNotFoundException;
import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import com.wanted.preonboarding.ticketing.domain.dto.request.CancelReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.request.ReadReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.response.CancelReservationResponse;
import com.wanted.preonboarding.ticketing.domain.dto.response.CreateReservationResponse;
import com.wanted.preonboarding.ticketing.domain.dto.response.ReadReservationResponse;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import com.wanted.preonboarding.ticketing.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticketing.domain.entity.Reservation;
import com.wanted.preonboarding.ticketing.repository.PerformanceRepository;
import com.wanted.preonboarding.ticketing.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticketing.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    private final AlarmService alarmService;
    private final ReservationValidator reservationValidator;

    @Transactional
    public CreateReservationResponse createReservation(CreateReservationRequest createReservationRequest) {
        Performance performance = findPerformance(createReservationRequest);
        Reservation reservation = reserveTicket(createReservationRequest, performance);
        reserveSeat(createReservationRequest);

        return reservation.toCreateReservationResponse(performance);
    }

    private Performance findPerformance(CreateReservationRequest createReservationRequest) {
        Performance performance = performanceRepository.findById(createReservationRequest.getPerformanceId())
                .orElseThrow(() -> new PerformanceNotFountException(ErrorCode.NOT_FOUND_PERFORMANCE));
        reservationValidator.validateBalance(createReservationRequest, performance);

        return performance;
    }

    private void reserveSeat(CreateReservationRequest createReservationRequest) {
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.getReferenceById(createReservationRequest.getSeatId());
        performanceSeatInfo.updateReservationStatus(createReservationRequest.getReservationStatus());
    }

    private Reservation reserveTicket(CreateReservationRequest createReservationRequest, Performance performance) {
        Reservation reservation = Reservation.from(createReservationRequest, performance);

        return reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public Page<ReadReservationResponse> readReservation(ReadReservationRequest reservationRequest, Pageable pageable) {
        Page<Reservation> reservations = findAllReservation(reservationRequest, pageable);

        return reservations.map(Reservation::toReadReservationResponse);
    }

    private Page<Reservation> findAllReservation(ReadReservationRequest reservationRequest, Pageable pageable) {
        Page<Reservation> reservations = reservationRepository.findByPhoneNumberAndName(reservationRequest.getPhoneNumber()
                , reservationRequest.getReservationName()
                , pageable);

        return reservationValidator.validateReservations(reservations);
    }

    @Transactional
    public List<CancelReservationResponse> cancelReservation(CancelReservationRequest cancelReservationRequest) {
        deleteReservation(cancelReservationRequest);
        PerformanceSeatInfo performanceSeatInfo = changeSeatInfo(cancelReservationRequest);

        return alarmService.sendAlarm(performanceSeatInfo);
    }

    private PerformanceSeatInfo changeSeatInfo(CancelReservationRequest cancelReservationRequest) {
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository
                .getReferenceById(cancelReservationRequest.getReservationSeatId());

        performanceSeatInfo.updateCancelStatus();

        return performanceSeatInfo;
    }

    private void deleteReservation(CancelReservationRequest cancelReservationRequest) {
        Reservation reservation = reservationRepository.findById(cancelReservationRequest.getReservationId())
                .orElseThrow(() -> new ReservationNotFoundException(ErrorCode.NOT_FOUND_RESERVATION));
        reservationRepository.delete(reservation);
    }
}