package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.dto.request.CreateReservationRequest;
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

    public BaseResDto getPerformanceInfoDetail(CreateReservationRequest createReservationRequest) {
        log.info("getPerformanceInfoDetail");
        Performance performance = getPerformance(createReservationRequest.getPerformanceId(), createReservationRequest.getRound());
        Reservation reservation = getReservation(createReservationRequest, performance);
        return ResponseReserveQueryDto.of(performance, reservation);
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
    public void createReservation(CreateReservationRequest createReservationRequest) {
        Performance performance = getPerformance(createReservationRequest.getPerformanceId(), createReservationRequest.getRound());
        String enableReserve = performance.getIsReserve();
        if (enableReserve.equalsIgnoreCase(ReserveStatus.ENABLE.getValue())) {
            // 1. 결제
            int price = performance.getPrice();
            createReservationRequest.setAmount(createReservationRequest.getAmount() - price);

            // 2. 예매 된 좌석인지 확인
            checkIsReserved(createReservationRequest);

            // 3. 예매 진행
            reservationRepository.save(Reservation.from(createReservationRequest));
        } else {
            throw new ServiceException(ResultCode.RESERVE_NOT_VALID);
        }
    }

    private void checkIsReserved(CreateReservationRequest reserveInfo) {
        reservationRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                                        reserveInfo.getPerformanceId(),
                                        reserveInfo.getRound(),
                                        reserveInfo.getLine(),
                                        reserveInfo.getSeat())
                .ifPresent(reservation -> {
                throw new ServiceException(ResultCode.ALREADY_EXISTS);
                });
    }

    public BaseResDto getReserveInfoDetail(RequestReserveQueryDto dto) {
        Reservation reservation = getReservation(dto);
        Performance performance = getPerformance(reservation.getPerformanceId(), reservation.getRound());
        return ResponseReserveQueryDto.of(performance, reservation);
    }

    private Reservation getReservation(RequestReserveQueryDto dto) {
        return reservationRepository.findByNameAndPhoneNumber(dto.getReservationName(), dto.getReservationPhoneNumber())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));
    }

}
