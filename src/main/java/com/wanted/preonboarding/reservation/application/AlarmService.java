package com.wanted.preonboarding.reservation.application;

import java.util.List;

import org.springframework.stereotype.Service;

import com.wanted.preonboarding.reservation.domain.dto.ReservationStateType;
import com.wanted.preonboarding.reservation.domain.dto.ReserveInfo;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import com.wanted.preonboarding.reservation.infrastructure.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AlarmService {
    
	private final ReservationRepository reservationRepository;
    
	/**
	 * 
	 * @param reserveInfo
	 * @return
	 */
	public boolean saveAlarm(ReserveInfo reserveInfo) {
		/** 기존 예약 여부에 따른 예약상태 설정*/
    	List<Reservation> reservationList = reservationRepository.findByPerformanceIdAndRoundAndLineAndSeatAndType(reserveInfo.getPerformanceId(), reserveInfo.getRound(), reserveInfo.getLine(), reserveInfo.getSeat(),ReservationStateType.WAITING.getCategory());
    	
		return false;
	};
}
