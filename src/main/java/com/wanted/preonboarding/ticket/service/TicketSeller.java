package com.wanted.preonboarding.ticket.service;

import com.wanted.preonboarding.performance.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performance.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.reservation.domain.dto.ReserveInfo;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import com.wanted.preonboarding.reservation.infrastructure.repository.ReservationRepository;

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
    private long totalAmount = 0L;
	/*
	 * public List<PerformanceInfo> getAllPerformanceInfoList() { return
	 * performanceRepository.findByIsReserve("enable") .stream()
	 * .map(PerformanceInfo::of) .toList(); }
	 * 
	 * public PerformanceInfo getPerformanceInfoDetail(String name) { return
	 * PerformanceInfo.of(performanceRepository.findByName(name)); }
	 */

	/*
	 * public boolean reserve(ReserveInfo reserveInfo) {
	 * log.info("reserveInfo ID => {}", reserveInfo.getPerformanceId()); Performance
	 * info = performanceRepository.findById(reserveInfo.getPerformanceId())
	 * .orElseThrow(EntityNotFoundException::new); String enableReserve =
	 * info.getIsReserve(); if (enableReserve.equalsIgnoreCase("enable")) { // 1. 결제
	 * int price = info.getPrice(); reserveInfo.setAmount(reserveInfo.getAmount() -
	 * price); // 2. 예매 진행
	 * //reservationRepository.save(Reservation.of(reserveInfo)); return true;
	 * 
	 * } else { return false; } }
	 */

}
