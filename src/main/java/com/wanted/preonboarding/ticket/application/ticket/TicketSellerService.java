package com.wanted.preonboarding.ticket.application.ticket;

import com.wanted.preonboarding.ticket.application.alarm.AlarmService;
import com.wanted.preonboarding.ticket.application.alarm.event.publisher.ReservationEventPublisher;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.assembler.PerformanceAssembler;
import com.wanted.preonboarding.ticket.domain.dto.assembler.ReservationAssembler;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceInfoResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationCancelResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReserveInfoResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.exception.exceptions.PerformanceDisableException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import static com.wanted.preonboarding.core.domain.util.Value.PERFORMANCE_ALL;
import static com.wanted.preonboarding.core.domain.util.Value.PERFORMANCE_DISABLE;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSellerService { //service
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final PaymentService paymentService;
    private final SeatService seatService;
    private final AlarmService alarmService;
    private final ReservationAssembler reservationAssembler;
    private final PerformanceAssembler performanceAssembler;
    private final ReservationEventPublisher reservationEventPublisher;
    @Transactional
    public ReserveInfoResponse reserve(ReservationRequest request)
    {
        Performance performance = checkEnablePerformance(request.getPerformanceId());

        paymentService.payReservation(request.getPaymentRequest(), performance);
        PerformanceSeatInfo performanceSeatInfo = seatService.reserveSeat(request, performance);

        Reservation reservation = reservationRepository.save(reservationAssembler.toReservationEntity(request, performanceSeatInfo));

        return reservationAssembler.toReserveInfoResponse(reservation, performanceSeatInfo, performance);
    }
    @Transactional
    public void saveCancelAlarm(ReservationRequest request)
    {
        alarmService.registerCancelAlarm(request.getPerformanceId(), request.getReserverInfoRequest());
    }
    public Performance checkEnablePerformance(String performanceId)
    {
        Performance performance = performanceRepository.findById(UUID.fromString(performanceId)).orElseThrow(EntityNotFoundException::new);
        if (performance.getIsReserve().equalsIgnoreCase(PERFORMANCE_DISABLE)) throw new PerformanceDisableException();
        return performance;
    }
    public List<PerformanceInfoResponse> getAllPerformanceInfoList(String able)
    {
        if(!able.equalsIgnoreCase(PERFORMANCE_ALL)) return getAllFindByIsReserve(able);
        return performanceRepository.findAll()
                .stream()
                .map(performanceAssembler::toDto)
                .toList();
    }
    public PerformanceInfoResponse getPerformanceInfoDetail(String name)
    {
        return performanceAssembler.toDto(performanceRepository.findByName(name));
    }
    public List<PerformanceInfoResponse> getAllFindByIsReserve(String able)
    {
        return performanceRepository.findByIsReserve(able).stream()
                .map(performanceAssembler::toDto)
                .collect(Collectors.toList());
    }
    public List<PerformanceInfoResponse> getAllPerformanceInfo()
    {
        return performanceRepository.findAll().stream().map(performanceAssembler::toDto).toList();
    }
    @Transactional
    public void cancelReservation(ReservationCancelRequest request)
    {
        Reservation reservation = reservationRepository.findById(request.getReservationId()).orElseThrow();
        Performance performance = performanceRepository.findById(reservation.getPerformanceId()).orElseThrow();

        seatService.deleteReservation(reservation);
        reservationRepository.delete(reservation);

        reservationEventPublisher.publishReservationCancelEvent(performance, reservation);
    }
}