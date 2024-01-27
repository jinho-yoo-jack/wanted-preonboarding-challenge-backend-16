package com.wanted.preonboarding.ticketing.service;

import com.wanted.preonboarding.ticketing.aop.advice.exception.NotFoundReservationException;
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
import com.wanted.preonboarding.ticketing.event.CancelReservationEvent;
import com.wanted.preonboarding.ticketing.repository.ReservationRepository;
import com.wanted.preonboarding.ticketing.service.discount.DiscountService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {
    private final ReservationRepository reservationRepository;

    private final ReservationValidator reservationValidator;
    private final ApplicationEventPublisher eventPublisher;

    private final DiscountService discountService;
    private final PerformanceService performanceService;
    private final PerformanceSeatInfoService performanceSeatInfoService;
    private final AlarmService alarmService;

    @Transactional
    public CreateReservationResponse createReservation(CreateReservationRequest createReservationRequest) {
        Performance performance = performanceService.findPerformance(createReservationRequest.getPerformanceId());
        Reservation reservation = reserveTicket(createReservationRequest, performance);
        int discountMoney = discountService.calculateMaximumDiscount(performance);
        reserveSeat(createReservationRequest);

        return reservation.toCreateReservationResponse(performance, discountMoney);
    }

    private void reserveSeat(CreateReservationRequest createReservationRequest) {
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoService
                .findSeatInfo(createReservationRequest.getSeatId());
        performanceSeatInfo.updateReservationStatus(createReservationRequest.getReservationStatus());
    }

    private Reservation reserveTicket(CreateReservationRequest createReservationRequest, Performance performance) {
        Reservation reservation = Reservation.from(createReservationRequest, performance);
        reservationValidator.validateBalance(createReservationRequest, performance);

        return reservationRepository.save(reservation);
    }

    @Transactional(readOnly = true)
    public Page<ReadReservationResponse> readReservations(ReadReservationRequest reservationRequest, Pageable pageable) {
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
    public CancelReservationResponse cancelReservation(CancelReservationRequest cancelReservationRequest) {
        PerformanceSeatInfo performanceSeatInfo = changeSeatInfo(cancelReservationRequest);
        deleteReservation(cancelReservationRequest);
        List<String> emails = deleteAlarm(performanceSeatInfo.getPerformance());
        eventPublisher.publishEvent(new CancelReservationEvent(emails, performanceSeatInfo.getId()));

        return performanceSeatInfo.toCancelReservationResponse();
    }

    private List<String> deleteAlarm(Performance performance) {
        return alarmService.deleteAlarmByPerformance(performance);
    }

    private PerformanceSeatInfo changeSeatInfo(CancelReservationRequest cancelReservationRequest) {
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoService
                .findSeatInfo(cancelReservationRequest.getReservationSeatId());
        performanceSeatInfo.updateCancelStatus();

        return performanceSeatInfo;
    }

    private void deleteReservation(CancelReservationRequest cancelReservationRequest) {
        Reservation reservation = reservationRepository.findById(cancelReservationRequest.getReservationId())
                .orElseThrow(() -> new NotFoundReservationException(ErrorCode.NOT_FOUND_RESERVATION));
        reservationRepository.delete(reservation);
    }
}