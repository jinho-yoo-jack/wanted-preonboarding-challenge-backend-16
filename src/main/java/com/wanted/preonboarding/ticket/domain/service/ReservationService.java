package com.wanted.preonboarding.ticket.domain.service;

import com.wanted.preonboarding.core.exception.PerformanceNotFoundException;
import com.wanted.preonboarding.core.exception.PerformanceSeatInfoNotFoundException;
import com.wanted.preonboarding.core.exception.ReservationNotFoundException;
import com.wanted.preonboarding.ticket.domain.code.ActiveType;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceId;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.ticket.infrastructure.ReservationRepository;
import com.wanted.preonboarding.ticket.interfaces.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final ReservationRepository reservationRepository;


    public ReservationResponseDto reserve(ReservationRequestDto reservationRequestDto) {

        Performance performance = performanceRepository.findById(new PerformanceId(reservationRequestDto.getPerformanceId(), reservationRequestDto.getRound())).orElseThrow(PerformanceNotFoundException::new);
        PerformanceSeatInfo performanceSeatInfo = performanceSeatInfoRepository.findByPerformanceAndLineAndSeat(performance, reservationRequestDto.getLine(), reservationRequestDto.getSeat())//todo 예약 가능!
                .orElseThrow(PerformanceSeatInfoNotFoundException::new);

        Reservation reservation = Reservation.newInstance(performance, performanceSeatInfo, reservationRequestDto.getReservationHolderName(), reservationRequestDto.getReservationHolderPhoneNumber());
        return ReservationResponseDto.newInstance(reservation);

    }

    @Transactional(readOnly = true)
    public ReservationInquiryDto getReservationInquiry(ReservationInquiryRequestDto dto) {
        Reservation reservation = reservationRepository.findByReservationHolderNameAndPhoneNumber(dto.getReservationHolderName(), dto.getPhoneNumber()).orElseThrow(ReservationNotFoundException::new);
        return ReservationInquiryDto.builder()
                .performanceId(reservation.getPerformance().getId().getId())
                .performanceName(reservation.getPerformance().getName())
                .round(reservation.getPerformance().getId().getRound())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .startDate(reservation.getPerformance().getStartDate())
                .reservationHolderName(reservation.getReservationHolderName())
                .reservationHolderPhoneNumber(reservation.getPhoneNumber())
                .build();
    }

    @Transactional(readOnly = true)
    public List<PerformanceDto> getPerformances() {
        List<Performance> performances = performanceRepository.findAllByIsReserve(ActiveType.OPEN);
        return performances.stream()
                .map(x -> PerformanceDto.builder()
                        .performanceId(x.getId().getId())
                        .name(x.getName())
                        .round(x.getId().getRound())
                        .startDate(x.getStartDate())
                        .isReserve(x.getIsReserve())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional(readOnly = true)
    public PerformanceDto getPerformanceDetail(UUID performanceId) {
        Performance performance = performanceRepository.findByIdId(performanceId);
        return PerformanceDto.builder()
                .performanceId(performance.getId().getId())
                .name(performance.getName())
                .round(performance.getId().getRound())
                .startDate(performance.getStartDate())
                .isReserve(performance.getIsReserve())
                .build();
    }

    public void notice() {
        //todo 공연, 회차, 좌석정보 -> 공연 회차 시작일시 좌석정보로 메일 전송
    }
}
