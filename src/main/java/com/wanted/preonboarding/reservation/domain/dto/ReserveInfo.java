package com.wanted.preonboarding.reservation.domain.dto;

import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * 공연 및 전시 정보 + 예약자 정보
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ReserveInfo {
	
	@NotNull(groups = ReserveGroupOrder.reservation.class)
    private UUID performanceId;
    @NotNull(groups = ReserveGroupOrder.selectReserveInfo.class)
	private String reservationName;
    @NotNull(groups = ReserveGroupOrder.selectReserveInfo.class)
    private String reservationPhoneNumber;
    
    private char line;
    private int seat;
    
    private int round;
    private int gate;
    
    private String reservationStatus; // 예약; 취소;
    
    public static ReserveInfo selectReserveInfo(Reservation entity){
    	return ReserveInfo.builder()
    			.performanceId(entity.getPerformanceId())
    			.reservationName(entity.getName())
    			.reservationPhoneNumber(entity.getPhoneNumber())
    			.line(entity.getLine())
    			.seat(entity.getSeat())
    			.round(entity.getRound())
    			.gate(entity.getGate())
    			.build();
    }
    
    
}