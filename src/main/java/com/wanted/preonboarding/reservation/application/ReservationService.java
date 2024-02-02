package com.wanted.preonboarding.reservation.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wanted.preonboarding.performance.infrastructure.repository.PerformanceRepository;
import com.wanted.preonboarding.reservation.domain.dto.ReserveInfo;
import com.wanted.preonboarding.reservation.infrastructure.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/**
 * 예약 관련 서비스
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ReservationService {
    private final PerformanceRepository performanceRepository;
    private final ReservationRepository reservationRepository;
    
    public List<ReserveInfo> selectReserveInfo(ReserveInfo reserveInfo){
    	List<ReserveInfo> selectReserveInfoList = reservationRepository.findByNameAndPhoneNumber(reserveInfo.getReservationName(), reserveInfo.getReservationPhoneNumber())
    			.stream()
    			.map(ReserveInfo::selectReserveInfo)
    			.toList();
    	return selectReserveInfoList;
    }
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
