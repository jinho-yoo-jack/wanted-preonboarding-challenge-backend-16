package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReadReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.CreateReservationResponse;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.aop.dto.BaseResDto;
import com.wanted.preonboarding.ticket.aop.ResultCode;
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

    public List<ResponsePerformanceInfo> readAllPerformances() {
        return performanceRepository.findByIsReserve(ReserveStatus.ENABLE.getValue())
            .stream()
            .map(ResponsePerformanceInfo::from)
            .toList();
    }

    private Reservation getReservation(CreateReservationRequest reserveInfo, Performance performance) {
        return reservationRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                performance.getId(), performance.getRound(), reserveInfo.getLine(), reserveInfo.getSeat())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));
    }

    private Performance getPerformance(UUID id, int round) {
        return performanceRepository.findByIdAndRound(id, round)
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));
    }

    @Transactional
    public BaseResDto createReservation(CreateReservationRequest createReservationRequest) {
        Performance performance = getPerformance(createReservationRequest.getPerformanceId(), createReservationRequest.getRound());

        //1. 예약 가능한 공연인지 확인
        if(performance.isReserve(ReserveStatus.ENABLE)) {

            //2. 예매 된 좌석인지 확인
            checkIsReservedPerformanceSeat(createReservationRequest);

            //3. 결제 가능한지 확인
            performance.checkHasEnoughBalance(createReservationRequest.getBalance());

            //4. 예약
            Reservation reservation = reservationRepository.save(Reservation.from(createReservationRequest));

            return CreateReservationResponse.of(performance, reservation);
        } else {
            throw new ServiceException(ResultCode.RESERVE_NOT_VALID);
        }
    }


    private void checkIsReservedPerformanceSeat(CreateReservationRequest createReservationRequest) {
        reservationRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                                        createReservationRequest.getPerformanceId(),
                                        createReservationRequest.getRound(),
                                        createReservationRequest.getLine(),
                                        createReservationRequest.getSeat())
                .ifPresent(reservation -> {
                throw new ServiceException(ResultCode.ALREADY_EXISTS_RESERVATION);
                });
    }

    public BaseResDto readReservation(ReadReservationRequest readReservationRequest) {
        Reservation reservation = getReservation(readReservationRequest);
        Performance performance = getPerformance(reservation.getPerformanceId(), reservation.getRound());
        return CreateReservationResponse.of(performance, reservation);
    }

    private Reservation getReservation(ReadReservationRequest readReservationRequest) {
        return reservationRepository.findByNameAndPhoneNumber(readReservationRequest.getReservationName(), readReservationRequest.getReservationPhoneNumber())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));
    }

}
