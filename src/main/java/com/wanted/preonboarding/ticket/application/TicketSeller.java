package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.global.dto.BaseResDto;
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
            .map(ResponsePerformanceInfo::from)
            .toList();
    }

    public BaseResDto getPerformanceInfoDetail(ReserveInfo info) {
        log.info("getPerformanceInfoDetail");

        Performance performance = performanceRepository.findByIdAndRound(info.getPerformanceId(), info.getRound())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));

        Reservation reservation = reservationRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                performance.getId(),performance.getRound(), info.getLine(), info.getSeat())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));

        return ResponseReserveQueryDto.of(performance, reservation);
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

            // 2. 예매 된 좌석인지 확인
            reservationRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                                            reserveInfo.getPerformanceId(),
                                            reserveInfo.getRound(),
                                            reserveInfo.getLine(),
                                            reserveInfo.getSeat())
                    .ifPresent(reservation -> {
                    throw new ServiceException(ResultCode.ALREADY_EXISTS);
                    });

            // 3. 예매 진행
            reservationRepository.save(Reservation.of(reserveInfo));
            return true;

        } else {
            return false;
        }
    }

    public BaseResDto getReserveInfoDetail(RequestReserveQueryDto dto) {
        Reservation reservation = reservationRepository.findByNameAndPhoneNumber(dto.getReservationName(), dto.getReservationPhoneNumber())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));

        ResponseReserveQueryDto responseQueryDto = ResponseReserveQueryDto.from(reservation);

        Performance performance = performanceRepository.findByIdAndRound(reservation.getPerformanceId(), reservation.getRound())
                .orElseThrow(() -> new ServiceException(ResultCode.NOT_FOUND));

        String performanceName = performance.getName();

        responseQueryDto.setPerformanceName(performanceName);

        return responseQueryDto;
    }

}
