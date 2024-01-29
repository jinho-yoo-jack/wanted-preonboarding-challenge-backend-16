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
    private String userName;
    private String userPhoneNumber;

    public static ReservationResponse of(Reservation entity) {
        return ReservationResponse.builder()
                .id(entity.getId())
                .performanceId(entity.getPerformanceId())
                .round(entity.getSeatInfo().getRound())
                .gate(entity.getSeatInfo().getGate())
                .line(entity.getSeatInfo().getLine())
                .seat(entity.getSeatInfo().getSeat())
                .userName(entity.getUserInfo().getName())
                .userPhoneNumber(entity.getUserInfo().getPhoneNumber())
                .build();
    }
}
