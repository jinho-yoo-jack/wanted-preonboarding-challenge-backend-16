package com.wanted.preonboarding.ticket.presentation.dto;

import com.wanted.preonboarding.ticket.application.dto.ReservationCreateParam;
import com.wanted.preonboarding.ticket.domain.entity.SeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import lombok.Getter;

import java.util.UUID;

@Getter
public class ReservationCreateRequest {
    private String reservationName;
    private String reservationPhoneNumber;
    private Long amount;
    private UUID performanceId;
    private Integer round;
    private Integer gate;
    private String line;
    private Integer seat;

    public ReservationCreateParam toDto(){
        return ReservationCreateParam.builder()
                .userInfo(UserInfo.builder()
                        .name(reservationName)
                        .phoneNumber(reservationPhoneNumber)
                        .build())
                .amount(amount)
                .performanceId(performanceId)
                .seatInfo(SeatInfo.builder()
                        .round(round)
                        .gate(gate)
                        .line(line)
                        .seat(seat)
                        .build())
                .build();
    }
}
