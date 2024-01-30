package com.wanted.preonboarding.ticket.presentation.dto;

import com.wanted.preonboarding.ticket.application.dto.ReservationCancelParam;
import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import lombok.Getter;

@Getter
public class ReservationCancelRequest {
    private Integer reservationId;
    private String userName;
    private String userPhoneNumber;

    public ReservationCancelParam toDto() {
        return ReservationCancelParam.builder()
                .reservationId(reservationId)
                .userInfo(UserInfo.builder()
                        .name(userName)
                        .phoneNumber(userPhoneNumber)
                        .build())
                .build();
    }
}
