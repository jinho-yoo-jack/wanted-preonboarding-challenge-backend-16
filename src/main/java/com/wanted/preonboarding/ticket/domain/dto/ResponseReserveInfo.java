package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
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
    private ResponsePerformanceSeatInfoDto seatInfo;
    private int gate;


    public ResponseReserveInfo (Reservation reservation, Performance performance, PerformanceSeatInfo performanceSeatInfo){

        ResponseReserveInfo.builder()
                .reservationName(reservation.getName())
                .reservationPhoneNumber(reservation.getPhoneNumber())
                .performanceName(performance.getName())
                .performanceId(performance.getId())
                .round(reservation.getRound())
                .seatInfo(performanceSeatInfo.convertResponseDto())
                .gate(reservation.getGate())
                .build();
    }

}
