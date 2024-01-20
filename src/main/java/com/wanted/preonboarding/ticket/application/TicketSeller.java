package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.dto.ReservationInfoRequest;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final PerformanceSeatRepository seatRepository;

    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
                .stream()
                .map(PerformanceInfo::of)
                .toList();
    }

    public ReserveInfo reserve(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
        Performance performanceInfo = performanceRepository.findById(reserveInfo.getPerformanceId())
                .orElseThrow(EntityNotFoundException::new);
        String enableReserve = performanceInfo.getIsReserve();

        int price = performanceInfo.getPrice();
        if (price > reserveInfo.getAmount()) throw new RuntimeException("잔액이 부족합니다.");

        if (!enableReserve.equalsIgnoreCase("enable")) {
            throw new RuntimeException("매진되었습니다.");
        }
        // 1. 결제
        // 예약정보에 잔액 반영
        reserveInfo.setAmount(reserveInfo.getAmount() - price);

        // 좌석 변동사항 반영
        // - 좌석 테이블
        UUID performanceId = performanceInfo.getId();
        int round = reserveInfo.getRound();
        char line = reserveInfo.getLine();
        int seat = reserveInfo.getSeat();
        PerformanceSeatInfo seatInfo = seatRepository
                .findByPerformanceIdAndRoundAndLineAndSeat(performanceId, round, line, seat);
        if (seatInfo.getIsReserve().equals("disable")) {
            throw new RuntimeException("이미 선점된 좌석입니다.");
        }
        seatInfo.setIsReserve("disable");
        seatRepository.save(seatInfo);

        // - 퍼포먼스 테이블 (만약 자리가 전부 disable 상태일때)
        disablePerformanceByIsReserve(performanceId);

        // 2. 예매 진행
        Reservation reservation = reservationRepository.save(Reservation.of(reserveInfo));
        return ReserveInfo.of(reservation);

    }

    // 좌석이 전부 소진되었을때 performance의 예매가능 여부를 변경한다.
    private void disablePerformanceByIsReserve(UUID performanceId) {
        boolean exists = seatRepository.existsByPerformanceIdAndIsReserve(performanceId, "enable");
        if (!exists) {
            Performance performance = performanceRepository.findById(performanceId)
                    .orElseThrow(EntityNotFoundException::new);
            performance.setIsReserve("disable");
            performanceRepository.save(performance);
        }
    }

    public List<ReserveInfo> getReservationInfos(ReservationInfoRequest reservationInfoRequest) {
        List<Reservation> reservations = reservationRepository.findByNameAndPhoneNumber(reservationInfoRequest.getName(),
                reservationInfoRequest.getPhoneNumber());

        return reservations.stream().map(ReserveInfo::of)
                .collect(Collectors.toList());
    }
}
