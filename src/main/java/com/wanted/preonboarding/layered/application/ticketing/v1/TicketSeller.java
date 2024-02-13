package com.wanted.preonboarding.layered.application.ticketing.v1;

import com.wanted.preonboarding.layered.application.ticketing.v1.policy.TicketFeePolicy;
import com.wanted.preonboarding.layered.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.layered.domain.dto.Ticket;
import com.wanted.preonboarding.layered.domain.entity.ticketing.Performance;
import com.wanted.preonboarding.layered.domain.entity.ticketing.Reservation;
import com.wanted.preonboarding.layered.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.layered.infrastructure.repository.ReservationRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

import java.util.*;

@Slf4j
public class TicketSeller {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    private final TicketFeePolicy appliedTicketFeePolicy;

    public TicketSeller(PerformanceRepository p, ReservationRepository r, TicketFeePolicy d){
        performanceRepository = p;
        reservationRepository = r;
        appliedTicketFeePolicy = d;
    }

    public List<PerformanceInfo> getAllPerformanceInfoList() {
        return performanceRepository.findByIsReserve("enable")
            .stream()
            .map(PerformanceInfo::of)
            .toList();
    }

    public PerformanceInfo getPerformanceInfoDetail(String name) {
        return PerformanceInfo.of(performanceRepository.findByName(name));
    }

    public int getTotalPrice(){
        return appliedTicketFeePolicy.calculateFee();
    }

    public boolean reserve(Ticket ticket) {
        log.info("reserveInfo ID => {}", ticket.getPerformanceId());
        Performance info = performanceRepository.findById(ticket.getPerformanceId())
            .orElseThrow(EntityNotFoundException::new);
        String enableReserve = info.getIsReserve();
        if (enableReserve.equalsIgnoreCase("enable")) {
            int price = getTotalPrice();
            ticket.setAmount(ticket.getAmount() - price);
            reservationRepository.save(Reservation.of(ticket));
            return true;
        } else {
            return false;
        }
    }

}
