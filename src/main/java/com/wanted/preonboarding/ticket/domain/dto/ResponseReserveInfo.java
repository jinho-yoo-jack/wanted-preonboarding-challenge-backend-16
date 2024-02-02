package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ResponseReserveInfo {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId; //공연ID
    private String performanceName; //공연명
    private String reservationName;
    private String reservationPhoneNumber;
    private int round;
    private int seat;
    private int gate;


    public ResponseReserveInfo (Reservation entity, Performance performance){
        ResponseReserveInfo.builder()
                .reservationName(entity.getName())
                .reservationPhoneNumber(entity.getPhoneNumber())
                .performanceName(performance.getName())
                .performanceId(performance.getId())
                .round(entity.getRound())
                .seat(entity.getSeat())
                .gate(entity.getGate())
                .build();
    }

}
