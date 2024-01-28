package com.wanted.preonboarding.ticket.presentation.dto;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ReservationResponse {
    private Integer id;
    private UUID performanceId;
    private Integer round;
    private Integer gate;
    private String line;
    private Integer seat;
    private String reservationName;
    private String reservationPhoneNumber;

    public static ReservationResponse of(Reservation entity) {
        return ReservationResponse.builder()
                .id(entity.getId())
                .performanceId(entity.getPerformanceId())
                .round(entity.getSeatInfo().getRound())
                .gate(entity.getSeatInfo().getGate())
                .line(entity.getSeatInfo().getLine())
                .seat(entity.getSeatInfo().getSeat())
                .reservationName(entity.getUserInfo().getName())
                .reservationPhoneNumber(entity.getUserInfo().getPhoneNumber())
                .build();
    }
}
