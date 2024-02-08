package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReadReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.CreateReservationResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceInfoResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.aop.StatusCode;
import com.wanted.preonboarding.ticket.aop.exception.ServiceException;
import com.wanted.preonboarding.ticket.global.common.ReserveStatus;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private long totalAmount = 0L;

    public List<PerformanceInfoResponse> readAllPerformances() {
        return performanceRepository.findByIsReserve(ReserveStatus.ENABLE.getValue())
            .stream()
            .map(PerformanceInfoResponse::from)
            .toList();
    }

    private Reservation getReservation(CreateReservationRequest dto, Performance performance) {
        return reservationRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                performance.getId(), performance.getRound(), dto.getLine(), dto.getSeat())
                .orElseThrow(() -> new ServiceException(StatusCode.NOT_FOUND));
    }

    private Performance getPerformance(UUID id, int round, ReserveStatus reserveStatus) {
        return performanceRepository.findByIdAndRoundAndIsReserve(id, round, reserveStatus.getValue())
                .orElseThrow(() -> new ServiceException(StatusCode.NOT_FOUND));
    }

    @Transactional
    public CreateReservationResponse createReservation(CreateReservationRequest dto) {

        //1. 예약 가능한 공연을 가져옴
        Performance performance = getPerformance(dto.getPerformanceId(), dto.getRound(), ReserveStatus.ENABLE);

        //2. 공연 좌석 자리가 있는지 가져옴
        PerformanceSeatInfo performanceSeatInfo = checkIsReservePerformanceSeat(dto.getPerformanceId()
                                                                                    , dto.getRound()
                                                                                    , dto.getLine()
                                                                                    , dto.getSeat());
        //3. 예약 가능한 좌석인지 확인
        performanceSeatInfo.isReserve(ReserveStatus.ENABLE);

        //4. 예약이 불가능하도록 수정
        performanceSeatInfo.updateIsReserve(ReserveStatus.DISABLE);


        //5. 이미 예약 되었는지 확인(3번 절차 이후 더블체크)
        checkIsReserveReservation(dto.getPerformanceId()
                                        ,dto.getRound()
                                        ,dto.getLine()
                                        ,dto.getSeat());

        //6. 결제 가능한지 확인
        performance.checkHasEnoughBalance(dto.getBalance());


        //7. 예약
        Reservation reservation = reservationRepository.save(Reservation.from(dto));

        return CreateReservationResponse.of(performance, reservation);
    }


    private PerformanceSeatInfo checkIsReservePerformanceSeat(UUID performanceId, int round, char line, int seat) {
        return performanceSeatInfoRepository.findByPerformanceIdAndRoundAndLineAndSeat(performanceId, round, line, seat)
                .orElseThrow(() -> new ServiceException(StatusCode.RESERVE_NOT_VALID_PERFORMANCE_SEAT));
    }

    private void checkIsReserveReservation(UUID performanceId, int round, char line, int seat) {
        reservationRepository.findByPerformanceIdAndRoundAndLineAndSeat(performanceId, round, line, seat)
                .ifPresent(reservation -> {
                    throw new ServiceException(StatusCode.ALREADY_EXISTS_RESERVATION);
                });
    }

    public CreateReservationResponse readReservation(ReadReservationRequest dto) {
        Reservation reservation = getReservation(dto.getReservationName(), dto.getReservationPhoneNumber());
        Performance performance = getPerformance(reservation.getPerformanceId(), reservation.getRound(), ReserveStatus.ENABLE);
        return CreateReservationResponse.of(performance, reservation);
    }

    private Reservation getReservation(String reservationName, String reservationPhoneNumber) {
        return reservationRepository.findByNameAndPhoneNumber(reservationName, reservationPhoneNumber)
                .orElseThrow(() -> new ServiceException(StatusCode.NOT_FOUND));
    }

}
