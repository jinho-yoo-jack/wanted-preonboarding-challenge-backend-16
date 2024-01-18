package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private long totalAmount = 0L;

    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    public PerformanceInfo getPerformanceInfoDetail(String name) {
        return PerformanceInfo.of(performanceRepository.findByName(name));
    }

    public ReserveInfo reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
        Performance info = performanceRepository.findById(reserveInfo.getPerformanceId())
            .orElseThrow(EntityNotFoundException::new);
        String enableReserve = info.getIsReserve();

        int price = info.getPrice();
        if (price >= reserveInfo.getAmount()) throw new RuntimeException("잔액이 부족합니다.");

        if (!enableReserve.equalsIgnoreCase("enable")) {
            throw new RuntimeException("예약할 수 없는 좌석입니다.");
        }
        // 1. 결제
        reserveInfo.setAmount(reserveInfo.getAmount() - price);
        // performance seat info 의 모든 performance id 의 is_reserve 가 'disable' 이면
        // performance.is_reserve가 disable된다.

        // todo select(if eq round,gate,line,seat,performance_id) -> performance_seat_info.is_reserve = disable
        // todo if count(performance-seat-info) == 0 -> performance.is_reserve = 'disable'

        // 2. 예매 진행
        Reservation reservation = reservationRepository.save(Reservation.of(reserveInfo));
        return ReserveInfo.builder()
                .performanceId(reservation.getPerformanceId())
                .reservationName(reservation.getName())
                .reservationPhoneNumber(reservation.getPhoneNumber())
                .reservationStatus("reserve")
                .amount(reserveInfo.getAmount())
                .round(reservation.getRound())
                .line(reservation.getLine())
                .seat(reservation.getSeat())
                .build();

    }

}
