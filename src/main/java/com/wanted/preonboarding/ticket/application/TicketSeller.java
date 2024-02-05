package com.wanted.preonboarding.ticket.application;

import com.wanted.preonboarding.ticket.domain.DiscountPolicy;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.exception.AlreadyReservationException;
import com.wanted.preonboarding.ticket.domain.exception.NotEnoughAmountException;
import com.wanted.preonboarding.ticket.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.infrastructure.repository.ReservationRepository;
import com.wanted.preonboarding.ticket.presentation.request.CreateReserveRequest;
import com.wanted.preonboarding.ticket.presentation.response.CreateReservationResponse;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final DiscountPolicy discountPolicy;
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

    public CreateReservationResponse reserve(CreateReserveRequest createReserveRequest) {
        log.info("reserveInfo ID => {}", createReserveRequest.getPerformanceId());
        Performance info = performanceRepository.findById(createReserveRequest.getPerformanceId())
            .orElseThrow(EntityNotFoundException::new);
        String enableReserve = info.getIsReserve();

        if (!enableReserve.equalsIgnoreCase("enable")) {
            throw new AlreadyReservationException("이미 예약된 공연입니다.");
        }

        long userAmount = createReserveRequest.getAmount();
        int discountPerformanceAmount = discountPolicy.calculateDiscountAmount(info.getPrice());

        if (userAmount < discountPerformanceAmount) {
            throw new NotEnoughAmountException("잔액이 부족합니다.");
        }

        // 1. 결제
        createReserveRequest.setAmount(userAmount - discountPerformanceAmount);
        // 2. 예매 진행
        reservationRepository.save(Reservation.of(createReserveRequest));

        return CreateReservationResponse.of(
            info.getRound(),
            info.getName(),
            createReserveRequest.getLine(),
            createReserveRequest.getSeat(),
            info.getId(),
            createReserveRequest.getReservationName(),
            createReserveRequest.getReservationPhoneNumber()
        );

    }

}
