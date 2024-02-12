package com.wanted.preonboarding.ticket.application.dto;

import com.wanted.preonboarding.ticket.domain.entity.SeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class ReservationCreateParam {
    private UserInfo userInfo;
    private Long amount;
    private UUID performanceId;
    private SeatInfo seatInfo;
}
