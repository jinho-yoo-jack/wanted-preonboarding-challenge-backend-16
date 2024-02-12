package com.wanted.preonboarding.ticket.application.dto;

import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationCancelParam {
    private Integer reservationId;
    private UserInfo userInfo;
}
