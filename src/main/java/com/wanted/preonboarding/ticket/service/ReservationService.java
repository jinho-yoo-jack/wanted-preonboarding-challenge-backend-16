package com.wanted.preonboarding.ticket.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;
import com.wanted.preonboarding.ticket.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.service.dto.request.ReservationRequestDto;
import com.wanted.preonboarding.ticket.service.dto.response.ReservationResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;

    @Transactional
    public ReservationResponseDto reserve(final ReservationRequestDto requestDto) {
        log.info("==================== Reservation Start ====================");
        final Performance findPerformance = getPerformance(requestDto);
        final PerformanceSeatInfo reserveSeatInfo = getReserveSeatInfo(requestDto);
        final int balance = pay(requestDto, findPerformance);

        reserveSeatInfo.validate();

        reservationRepository.save(requestDto.toEntity(findPerformance));
        reserveSeatInfo.updateReservationStatus(ReservationStatus.OCCUPIED);

        return ReservationResponseDto.builder()
                .reservationName(requestDto.reservationName())
                .reservationPhoneNumber(requestDto.reservationPhoneNumber())
                .performanceId(requestDto.performanceId())
                .performanceName(findPerformance.getName())
                .amount(balance)
                .round(requestDto.round())
                .line(requestDto.line())
                .seat(requestDto.seat())
                .build();
    }

    private PerformanceSeatInfo getReserveSeatInfo(final ReservationRequestDto requestDto) {
        return performanceSeatInfoRepository.findByPerformanceIdAndRoundAndLineAndSeat(
                        requestDto.performanceId(), requestDto.round(), requestDto.line(), requestDto.seat())
                .orElseThrow(() -> new IllegalArgumentException("Not Found Seat"));
    }

    private Performance getPerformance(final ReservationRequestDto requestDto) {
        return performanceRepository.findById(requestDto.performanceId())
                .orElseThrow(() -> new IllegalArgumentException("Not Found Performance"));
    }

    // TODO: 결제 로직 분리
    private int pay(final ReservationRequestDto reservationRequestDto, final Performance findPerformance) {
        final int amount = reservationRequestDto.amount();
        final int price = findPerformance.getPrice();
        final int balance = amount - price;

        if (balance < 0) {
            throw new IllegalArgumentException("Not Enough Balance");
        }
        return balance;
    }
}
