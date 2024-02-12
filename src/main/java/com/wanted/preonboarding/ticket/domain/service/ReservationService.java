package com.wanted.preonboarding.ticket.domain.service;

import com.wanted.preonboarding.ticket.domain.delegator.ReservationValidateDelegator;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.factory.ReservationReadSimpleFactory;
import com.wanted.preonboarding.ticket.infrastructure.reader.PerformanceReader;
import com.wanted.preonboarding.ticket.infrastructure.reader.ReservationReader;
import com.wanted.preonboarding.ticket.infrastructure.store.PerformanceSeatInfoStore;
import com.wanted.preonboarding.ticket.infrastructure.store.ReservationStore;
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

    private final ReservationReadSimpleFactory reservationReadSimpleFactory;
    private final ReservationValidateDelegator reservationValidateDelegator;
    private final ReservationStore reservationStore;
    private final PerformanceReader performanceReader;
    private final PerformanceSeatInfoStore performanceSeatInfoStore;
    private final ReservationReader reservationReader;


    public ReservationResponseDto reserve(ReservationRequestDto reservationRequestDto) {
        ReservationReadDto reservationReadDto = reservationReadSimpleFactory.getReservationReadDto(reservationRequestDto);
        reservationValidateDelegator.validate(reservationReadDto);

        Reservation reservation = Reservation.newInstance(reservationReadDto.getPerformance(), reservationReadDto.getPerformanceSeatInfo(), reservationReadDto.getReservationHolderName(), reservationReadDto.getReservationHolderPhoneNumber());
        PerformanceSeatInfo performanceSeatInfo = reservationReadDto.getPerformanceSeatInfo();
        performanceSeatInfo.reserved();
        performanceSeatInfoStore.store(performanceSeatInfo);
        return ReservationResponseDto.newInstance(reservationStore.store(reservation));
    }

    @Transactional(readOnly = true)
    public ReservationInquiryDto getReservationInquiry(ReservationInquiryRequestDto dto) {
        Reservation reservation = reservationReader.getReservationInquiry(dto.getReservationHolderName(), dto.getPhoneNumber());
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
        List<Performance> performances = performanceReader.getOpeningPerformances();
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
        Performance performance = performanceReader.getById(performanceId);
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
