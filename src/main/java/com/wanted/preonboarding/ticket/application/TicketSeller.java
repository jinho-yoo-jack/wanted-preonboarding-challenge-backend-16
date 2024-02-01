package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.global.dto.BaseResDto;
import com.wanted.preonboarding.ticket.global.exception.InvalidInputException;
import com.wanted.preonboarding.ticket.global.exception.ResultCode;
import com.wanted.preonboarding.ticket.global.exception.ServiceException;
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

    public List<ResponsePerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
            .stream()
            .map(ResponsePerformanceInfo::of)
            .toList();
    }

    public BaseResDto getPerformanceInfoDetail(ReserveInfo info) {
        log.info("getPerformanceInfoDetail");

        Performance performance = performanceRepository.findByIdAndRound(info.getPerformanceId(), info.getRound())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));

        Reservation reservation = reservationRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                performance.getId(),performance.getRound(), info.getLine(), info.getSeat())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));

        return ResponseReserveQueryDto
                .builder()
                .performanceId(performance.getId())
                .performanceName(performance.getName())
                .round(reservation.getRound())
                .gate(reservation.getGate())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .reservationName(reservation.getName())
                .reservationPhoneNumber(reservation.getPhoneNumber())
                .build();
    }

    @Transactional
    public boolean reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
        Performance info = performanceRepository.findByIdAndRound(reserveInfo.getPerformanceId(), reserveInfo.getRound())
            .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));

        String enableReserve = info.getIsReserve();
        if (enableReserve.equalsIgnoreCase("enable")) {
            // 1. 결제
            int price = info.getPrice();
            reserveInfo.setAmount(reserveInfo.getAmount() - price);
            // 2. 예매 진행
            reservationRepository.save(Reservation.of(reserveInfo));
            return true;

        } else {
            return false;
        }
    }

    public BaseResDto getReserveInfoDetail(RequestReserveQueryDto dto) {
        Optional<Reservation> optionalReservation = reservationRepository.findByNameAndPhoneNumber(dto.getReservationName(), dto.getReservationPhoneNumber());

        if(!optionalReservation.isPresent()) {
            throw new InvalidInputException("예약 고객 정보가 없습니다.");
        }

        ResponseReserveQueryDto responseQueryDto = ResponseReserveQueryDto.of(optionalReservation.get());
        Reservation reservation = optionalReservation.get();
        Optional<Performance> optionalPerformance = performanceRepository.findById(reservation.getPerformanceId());
        if(!optionalPerformance.isPresent()) {
            throw new IllegalArgumentException("예약한 공연 정보가 없습니다.");
        }
        String performanceName = optionalPerformance.get().getName();

        responseQueryDto.setPerformanceName(performanceName);

        return responseQueryDto;
    }





}
