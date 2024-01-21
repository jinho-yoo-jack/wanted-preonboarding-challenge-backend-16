package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.dto.ReservationInfoRequest;
import com.wanted.preonboarding.ticket.exception.InsufficientAmountException;
import com.wanted.preonboarding.ticket.exception.UnavailableReserveException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceSeatRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

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

    public ReserveInfo createReservation(ReserveInfo reserveInfo) {
        log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId());
        Performance performanceInfo = performanceRepository.findById(reserveInfo.getPerformanceId())
                .orElseThrow(EntityNotFoundException::new);
        UUID performanceId = performanceInfo.getId();

        // 1. 결제
        int price = getPrice(reserveInfo, performanceInfo);
        reserveInfo.setAmount(reserveInfo.getAmount() - price);

        // 2. 예매 진행
        reserveSeat(getPerformanceSeatInfo(reserveInfo));
        disablePerformanceByIsReserve(performanceId);

        return ReserveInfo.of(reservationRepository.save(Reservation.of(reserveInfo, performanceInfo)));

    }

    private void reserveSeat(PerformanceSeatInfo seatInfo){
        if (seatInfo.getIsReserve().equals("disable")) {
            throw new UnavailableReserveException("이미 선점된 좌석입니다.");
        }
        seatInfo.setIsReserve("disable");
        seatRepository.save(seatInfo);
    }

    private PerformanceSeatInfo getPerformanceSeatInfo(ReserveInfo reserveInfo) {
        return seatRepository.findByPerformanceIdAndRoundAndLineAndSeat(reserveInfo.getPerformanceId(),
                                                            reserveInfo.getRound(),
                                                            reserveInfo.getLine(),
                                                            reserveInfo.getSeat());
    }

    private int getPrice(ReserveInfo reserveInfo, Performance performanceInfo) {
        boolean isReserveEnable = performanceInfo.getIsReserve().equalsIgnoreCase("enable");
        if (!isReserveEnable) throw new UnavailableReserveException("매진되었습니다.");

        int price = performanceInfo.getPrice();
        long amount = reserveInfo.getAmount();
        if (price > amount) throw new InsufficientAmountException("잔액이 부족합니다.");

        return price;
    }

    // 좌석이 전부 소진되었을때 performance의 예매가능 여부를 변경한다.
    private void disablePerformanceByIsReserve(UUID performanceId) {
        boolean isReserve = seatRepository.existsByPerformanceIdAndIsReserve(performanceId, "enable");
        if (!isReserve) {
            Performance performance = performanceRepository.findById(performanceId)
                    .orElseThrow(EntityNotFoundException::new);
            performance.setIsReserve("disable");
            performanceRepository.save(performance);
        }
    }

    public List<ReserveInfo> getReservationInfos(ReservationInfoRequest reservationInfoRequest) {
        String name = reservationInfoRequest.getName();
        String phoneNumber = reservationInfoRequest.getPhoneNumber();
        List<Reservation> reserveInfos = reservationRepository.findByNameAndPhoneNumber(name, phoneNumber);
        if (reserveInfos.isEmpty()) throw new EntityNotFoundException();
        return reserveInfos.stream()
                .map(ReserveInfo::of)
                .toList();
    }
}
