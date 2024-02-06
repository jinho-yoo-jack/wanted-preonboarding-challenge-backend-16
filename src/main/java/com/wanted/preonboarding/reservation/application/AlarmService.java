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
	 * 알람 확인 후 전송 서비스
	 * @param reserveInfo
	 * @return
	 */
	public boolean saveAlarm(ReserveInfo reserveInfo) {
		/** 기존 예약 여부에 따른 예약상태 설정*/
    	List<Reservation> reservationList = reservationRepository.findByPerformanceIdAndRoundAndLineAndSeatAndType(reserveInfo.getPerformanceId(), reserveInfo.getRound(), reserveInfo.getLine(), reserveInfo.getSeat(),ReservationStateType.WAITING.getCategory());
    	
    	
    	
		return sendMessage(reservationList);
	};
	
	/**
	 * 메세지 전송 서비스
	 * @param reservationList
	 * @return
	 */
	private boolean sendMessage(List<Reservation> reservationList) {
		/**
		 * 조회 후 서비스 구현
		 * 
		 * */
		return true;
	}
}
