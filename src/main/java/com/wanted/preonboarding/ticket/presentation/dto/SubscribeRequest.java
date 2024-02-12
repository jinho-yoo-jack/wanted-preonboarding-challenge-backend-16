package com.wanted.preonboarding.ticket.presentation.dto;

import com.wanted.preonboarding.ticket.application.dto.SubscribeParam;
import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import lombok.Getter;

import java.util.UUID;

@Getter
public class SubscribeRequest {
    private UUID performanceId;
    private String userName;
    private String userPhoneNumber;

    public SubscribeParam toDto() {
        return SubscribeParam.builder()
                .performanceId(performanceId)
                .userInfo(UserInfo.builder()
                        .name(userName)
                        .phoneNumber(userPhoneNumber)
                        .build())
                .build();
    }
}
