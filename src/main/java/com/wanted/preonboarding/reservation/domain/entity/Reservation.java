package com.wanted.preonboarding.reservation.domain.entity;

import java.util.Arrays;
import java.util.UUID;

import com.wanted.preonboarding.reservation.domain.dto.ReservationStateType;
import com.wanted.preonboarding.reservation.domain.dto.ReserveInfo;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "reservation_id")
    private int reservationId;
    
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;
    
    @Column(nullable = false)
    private String name;
    
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;
    
    @Column(nullable = false)
    private int round;
    
    @Column(nullable = false)
    private int gate;
    
    @Column(nullable = false)
    private String line;
    
    @Column(nullable = false)
    private int seat;
    
    @Column(nullable = false)
    private int type;
    
    public static Reservation of(ReserveInfo info) {
        return Reservation.builder()
            .performanceId(info.getPerformanceId())
            .name(info.getReservationName())
            .phoneNumber(info.getReservationPhoneNumber())
            .round(info.getRound())
            .gate(info.getGate())
            .line(info.getLine())
            .seat(info.getSeat())
            .type(convertNameToCode(info.getType()))
            .build();
    }
    
    /**
     * 예약 타입 맵핑
     * @param code
     * @return
     */
    private static int convertNameToCode(String name){
        return Arrays.stream(ReservationStateType.values()).filter(value -> value.name() == name)
            .findFirst()
            .orElse(ReservationStateType.CANCELLATION)
            .getCategory();
    }
}
