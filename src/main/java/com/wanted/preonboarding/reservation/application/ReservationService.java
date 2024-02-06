package com.wanted.preonboarding.reservation.application;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wanted.preonboarding.common.exception.custom.CustomNullPointerException;
import com.wanted.preonboarding.performance.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.performance.infrastructure.repository.PerformanceSeatInfoRepository;
import com.wanted.preonboarding.reservation.domain.dto.ReservationStateType;
import com.wanted.preonboarding.reservation.domain.dto.ReserveInfo;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import com.wanted.preonboarding.reservation.infrastructure.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;

/**
 * 예약 관련 서비스
 */
@Service
@RequiredArgsConstructor
public class ReservationService {
	private final DiscountService discountService;
	private final AlarmService alarmService;
    private final PerformanceSeatInfoRepository performanceSeatInfoRepository;
    private final ReservationRepository reservationRepository;
    
    public List<ReserveInfo> selectReserveInfo(ReserveInfo reserveInfo){
    	List<ReserveInfo> selectReserveInfoList = reservationRepository.findByNameAndPhoneNumber(reserveInfo.getReservationName(), reserveInfo.getReservationPhoneNumber())
    			.stream()
    			.map(ReserveInfo::selectReserveInfo)
    			.toList();
    	return selectReserveInfoList;
    }
    
    public ReserveInfo saveReservation(ReserveInfo reserveInfo){
    	PerformanceInfo performanceInfo = PerformanceInfo.detailPerformanceList(performanceSeatInfoRepository.findByPerformanceIdAndRoundAndLineAndSeat(reserveInfo.getPerformanceId(),reserveInfo.getRound(),reserveInfo.getLine(),reserveInfo.getSeat()));
    	/** 금액 체크*/
    	if(!discountService.chkAmount(reserveInfo,performanceInfo)) {
    		return ReserveInfo.builder()
    				.reservationType(convertCodeToName(ReservationStateType.NOT_ENOUGH_MONEY.getCategory()))
    				.message("금액이 부족합니다.")
    				.build();
    	}
    	reservationRepository.save(Reservation.of(setStateReserveInfo(performanceInfo, reserveInfo)));
    	return ReserveInfo.builder()
    			.reservationName(reserveInfo.getReservationName())
    			.reservationPhoneNumber(reserveInfo.getReservationPhoneNumber())
    			.performanceId(reserveInfo.getPerformanceId())
    			.round(reserveInfo.getRound())
    			.seat(reserveInfo.getSeat())
    			.line(reserveInfo.getLine())
				.reservationType(reserveInfo.getType())
				.message("예약에 성공하셧습니다.")
				.build();
    }
    
    public ReserveInfo cancelReservation(ReserveInfo reserveInfo){
    	/** Exception Advisor에 사용할 커스텀 방법 예시*/
    	try {
    		Reservation updateReserveInfo = reservationRepository.findByPerformanceIdAndNameAndPhoneNumberAndTypeNot(reserveInfo.getPerformanceId(), reserveInfo.getReservationName(), reserveInfo.getReservationPhoneNumber(),ReservationStateType.CANCELLATION.getCategory());
    		updateReserveInfo.setType(ReservationStateType.CANCELLATION.getCategory());
    		reservationRepository.save(updateReserveInfo);
    		alarmService.saveAlarm(reserveInfo);
    		return ReserveInfo.builder()
        			.reservationType(reserveInfo.getType())
        			.message("예약이 취소되었습니다.")
        			.build();
		} catch (NullPointerException e) {
			throw new CustomNullPointerException("cancelReservation >> DataIntegrityViolationException");
		}
    }
    
    private ReserveInfo setStateReserveInfo(PerformanceInfo performanceInfo,ReserveInfo reserveInfo) {
    	/** 게이트 설정*/
    	reserveInfo.setGate(performanceInfo.getGate());
    	/** 기존 예약 여부에 따른 예약상태 설정*/
    	List<Reservation> reservationList = reservationRepository.findByPerformanceIdAndRoundAndLineAndSeat(reserveInfo.getPerformanceId(), reserveInfo.getRound(), reserveInfo.getLine(), reserveInfo.getSeat());
    	/** 기존 예약이 존재하지 않을경우 예약상태로 저장*/
    	if(reservationList.isEmpty()) {
    		reserveInfo.setType(convertCodeToName(ReservationStateType.RESERVATION.getCategory()));
    	}else {
    		/** 기존 예약이 존재할 경우 대기 상태로 저장*/
    		reserveInfo.setType(convertCodeToName(ReservationStateType.WAITING.getCategory()));
    	}
    	return reserveInfo;
    }
    
    /**
     * 예약 타입 맵핑
     * @param code
     * @return
     */
    private static String convertCodeToName(int code){
        return Arrays.stream(ReservationStateType.values()).filter(value -> value.getCategory() == code)
            .findFirst()
            .orElse(ReservationStateType.CANCELLATION)
            .name();
    }
    
}
