package com.wanted.preonboarding.layered.application.ticketing.v2;

import com.wanted.preonboarding.layered.application.ticketing.v1.policy.TicketFeePolicy;
import com.wanted.preonboarding.layered.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.layered.domain.entity.ticketing.Performance;
import com.wanted.preonboarding.layered.domain.entity.ticketing.Reservation;
import com.wanted.preonboarding.layered.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.layered.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class TicketSellerV2 {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final TicketFeePolicy discountPolicy;

    public TicketSellerV2(PerformanceRepository p, ReservationRepository r, TicketFeePolicy d) {
        performanceRepository = p;
        reservationRepository = r;
        discountPolicy = d;
    }

    public Performance getPerformanceInfo(UUID performanceId) {
        return performanceRepository.findById(performanceId)
            .orElseThrow(EntityNotFoundException::new);
    }

    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    public Reservation bookingReservation(Reservation r){
        return reservationRepository.save(r);
    }

    public PerformanceInfo getPerformanceInfoDetail(String name) {
        return PerformanceInfo.of(performanceRepository.findByName(name));
    }

    public int getDiscountedTotalAmount(int price) {
        return 0;
    }

}
