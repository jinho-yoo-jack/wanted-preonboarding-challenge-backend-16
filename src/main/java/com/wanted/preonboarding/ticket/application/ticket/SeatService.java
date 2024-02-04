package com.wanted.preonboarding.ticket.application.ticket;

import com.wanted.preonboarding.ticket.application.alarm.event.publisher.ReservationEventPublisher;
import com.wanted.preonboarding.ticket.domain.dto.request.AddPerformance;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.SeatRequest;
import com.wanted.preonboarding.ticket.domain.dto.assembler.PerformanceAssembler;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceInfoResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.exception.exceptions.ThisSeatDisableException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.wanted.preonboarding.core.domain.util.Value.SEAT_DISABLE;

@Transactional(readOnly = true)
@Service
@Slf4j
@RequiredArgsConstructor
public class SeatService {
    private final PerformanceRepository performanceRepository;
    private final PerformanceAssembler performanceAssembler;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final ReservationEventPublisher reservationEventPublisher;
    @Transactional
    public PerformanceInfoResponse addPerformance(AddPerformance addPerformance)
    {
        return performanceAssembler.toDto(
                performanceRepository.save(performanceAssembler.toPerformance(addPerformance))
        );
    }
    @Transactional
    public PerformanceSeatInfo reserveSeat(ReservationRequest request, Performance performance)
    {
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository
                .findByPerformanceIdAndSeatInfo(performance.getId(), request.getSeatRequest().toSeatInfo());
        if(performanceSeatInfo.getIsReserve().equalsIgnoreCase(SEAT_DISABLE)){
            saveCancelReservationAlarm(request);
            throw new ThisSeatDisableException();
        }
        performanceSeatInfo.reserved();
        return performanceSeatInfo;
    }
    private void saveCancelReservationAlarm(ReservationRequest request)
    {
        if(request.isCancelAlarm())
            reservationEventPublisher.publishSaveReservationCancelEvent(request);
    }

    @Transactional
    public void deleteReservation(Reservation reservation) {
        PerformanceSeatInfo performanceSeatInfo = reservation.getPerformanceSeatInfo();
        performanceSeatInfo.cancelReservation();
    }
}