package com.wanted.preonboarding.reservation.domain.dto;

import java.util.Arrays;
import java.util.UUID;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.wanted.preonboarding.reservation.domain.entity.Reservation;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 공연 및 전시 정보 + 예약자 정보
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_DEFAULT)
public class ReserveInfo {
	
	@NotNull(groups = ReserveGroupOrder.reservation.class)
    private UUID performanceId;
	
    @NotNull(groups = ReserveGroupOrder.selectReserveInfo.class)
    @NotNull(groups = ReserveGroupOrder.reservation.class)
	private String reservationName;
    
    @NotNull(groups = ReserveGroupOrder.selectReserveInfo.class)
    @NotNull(groups = ReserveGroupOrder.reservation.class)
    private String reservationPhoneNumber;
    
    @NotNull(groups = ReserveGroupOrder.reservation.class)
    private String line;
    
    @NotNull(groups = ReserveGroupOrder.reservation.class)
    private Integer  seat;
    
    @NotNull(groups = ReserveGroupOrder.reservation.class)
    private Integer  round;
    
    private int gate;
    
    private String type;
    
    @NotNull(groups = ReserveGroupOrder.reservation.class)
    private Integer  amount;
    
    private String reservationType;
    private String message;
    
    private int reservationStatus; // 예약; 취소;
    
    public static ReserveInfo selectReserveInfo(Reservation entity){
    	return ReserveInfo.builder()
    			.performanceId(entity.getPerformanceId())
    			.reservationName(entity.getName())
    			.reservationPhoneNumber(entity.getPhoneNumber())
    			.line(entity.getLine())
    			.seat(entity.getSeat())
    			.round(entity.getRound())
    			.gate(entity.getGate())
    			.type(convertReservationStateToName(entity.getType()))
    			.build();
    }
    
    /**
     * 예약상태 타입 맵핑
     * @param code
     * @return
     */
    private static String convertReservationStateToName(int code){
        return Arrays.stream(ReservationStateType.values()).filter(value -> value.getCategory() == code)
            .findFirst()
            .orElse(ReservationStateType.CANCELLATION)
            .name();
    }
    
}