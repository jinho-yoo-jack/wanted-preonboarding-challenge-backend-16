package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.*;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
@Transactional
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final AlarmApp alarmApp;
    private long totalAmount = 0L;

    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    public ResponseReserveInfo reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
        Performance info = performanceRepository.findById(reserveInfo.getPerformanceId())
            .orElseThrow(EntityNotFoundException::new);

        if (!"enable".equals(info.getIsReserve())) {
            throw new IllegalStateException("예약에 실패 하였습니다.");
        }
        try {
            // 1. 결제
            int price = info.getPrice();
            long discountedPrice = applyDiscount(price, reserveInfo.getDiscountPolicy());

            if (reserveInfo.getAmount() < discountedPrice) {
                throw new IllegalStateException("잔액이 부족 합니다.");
            }
            reserveInfo.setAmount(reserveInfo.getAmount() - discountedPrice);
            // 2. 예매 진행
            Reservation reservation = reservationRepository.save(Reservation.of(reserveInfo));
            return new ResponseReserveInfo(reservation,info);
        } catch (Exception e) {
            throw new IllegalStateException("예약에 실패하였습니다.", e);
        }
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
            responseReserveInfos.add(new ResponseReserveInfo(reservation,performance));
        }

        return responseReserveInfos;
    }


    public PerformanceInfo getPerformanceInfoDetail(String name) {
        Performance performance = performanceRepository.findByName(name)
                .orElseThrow(EntityNotFoundException::new);
        return PerformanceInfo.of(performance);
    }

    public void cancelReservation(CancelReservationRequestDto requestDto) {
        List<Reservation> reservations = reservationRepository.findByNameAndPhoneNumber(requestDto.getReservationName(), requestDto.getReservationPhoneNumber());

        for (Reservation reservation : reservations) {
            performanceRepository.deleteById(reservation.getPerformanceId());
        }
        if (!reservations.isEmpty()) {
            alarmApp.sendAlarm(reservations.get(0).getPerformanceId());
        }
    }


}
