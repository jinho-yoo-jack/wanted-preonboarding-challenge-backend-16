package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final PerformanceSeatRepository performanceSeatRepository;
    private final AlarmApp alarmApp;
    private long totalAmount = 0L;

    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    public List<ResponseReserveInfo> reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());

        Performance performanceInfo = performanceRepository.findById(reserveInfo.getPerformanceId())
            .orElseThrow(EntityNotFoundException::new);

        if (!"enable".equals(performanceInfo.getIsReserve())) {
            throw new IllegalStateException("예약에 실패 하였습니다.");
        }

        try {
            // 1. 결제
            int price = performanceInfo.getPrice();
            long discountedPrice = applyDiscount(price, reserveInfo.getDiscountPolicy());
            if (reserveInfo.getAmount() < discountedPrice) {
                throw new IllegalStateException("잔액이 부족 합니다.");
            }

            // 2. 예매 진행
            reserveInfo.setAmount(reserveInfo.getAmount() - discountedPrice);
            return getResponseReserveInfos(reserveInfo, performanceInfo);

        } catch (Exception e) {
            throw new IllegalStateException("예약에 실패하였습니다.", e);
        }
    }

    private List<ResponseReserveInfo> getResponseReserveInfos(ReserveInfo reserveInfo, Performance performanceInfo) {
        List<String> seatInfo = reserveInfo.getSeats();
        List<ResponseReserveInfo> reserveInfos = new ArrayList<>();

        for (String seat: seatInfo) {
            PerformanceSeatInfo convertSeatInfo = PerformanceSeatInfo.convertSeatInfo(seat);
            PerformanceSeatInfo reserveSeatInfo = performanceSeatRepository
                    .findByPerformanceNameAndSeatLineAndSeatNumber(performanceInfo.getName(),convertSeatInfo.getSeatLine(),convertSeatInfo.getSeatNumber())
                    .orElseThrow(()-> new IllegalArgumentException("해당 좌석은 없는 좌석 입니다."));

            Reservation reservation = reservationRepository.save(Reservation.of(reserveInfo, reserveSeatInfo));
            reserveInfos.add(new ResponseReserveInfo(reservation, performanceInfo,reserveSeatInfo));
        }

        return reserveInfos;
    }

    private long applyDiscount(int originalPrice, String discountPolicyString) {
        if (discountPolicyString == null) {
            return (long) (originalPrice * (1 - DiscountPolicy.DEFAULT.getDiscountRate()));
        }

        DiscountPolicy discountPolicy = DiscountPolicy.valueOf(discountPolicyString);
        return (long) (originalPrice * (1 - discountPolicy.getDiscountRate()));
    }

    public List<ResponseReserveInfo> getReserveInfo(GetReservationRequestDto requestDto){
        List<Reservation> reservations = reservationRepository.findByNameAndPhoneNumber(requestDto.getReservationName(), requestDto.getReservationPhoneNumber());
        List<ResponseReserveInfo> responseReserveInfos = new ArrayList<>();

        for (Reservation reservation : reservations) {
            Performance performance = performanceRepository.findById(reservation.getPerformanceId())
                    .orElseThrow(EntityNotFoundException::new);
            PerformanceSeatInfo seatInfo = performanceSeatRepository.findByPerformanceNameAndSeatLineAndSeatNumber(performance.getName(),reservation.getLine(),reservation.getSeat())
                    .orElseThrow(()->new IllegalArgumentException("해당 예매는 예약된 예매가 아닙니다."));

            responseReserveInfos.add(new ResponseReserveInfo(reservation,performance,seatInfo));
        }

        return responseReserveInfos;
    }

    public PerformanceInfo getPerformanceInfoDetail(String name) {
        Performance performance = performanceRepository.findByName(name)
                .orElseThrow(EntityNotFoundException::new);
        return PerformanceInfo.of(performance);
    }

    public void cancelReservation(CancelReservationRequestDto requestDto) {
        PerformanceSeatInfo performanceSeatInfo = performanceSeatRepository.findByPerformanceNameAndSeatLineAndSeatNumber(requestDto.getPerformanceName(),requestDto.getLine(),requestDto.getSeat())
                .orElseThrow(()->new IllegalArgumentException("예매 내역이 존재하지 않습니다"));
        Performance performance = performanceRepository.findByName(requestDto.getPerformanceName())
                .orElseThrow(()->new IllegalArgumentException("존재하지 않는 공연 입니다."));

        //좌석 닫기
        performanceSeatInfo.closedSeat();
        //알람 보내기
        alarmApp.sendAlarm(performance.getId());
    }


}
