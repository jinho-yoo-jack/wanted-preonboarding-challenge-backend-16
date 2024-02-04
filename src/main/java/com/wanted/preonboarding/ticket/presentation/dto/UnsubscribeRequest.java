package com.wanted.preonboarding.ticket.presentation.dto;

import com.wanted.preonboarding.ticket.application.dto.UnsubscribeParam;
import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import lombok.Getter;

@Getter
public class UnsubscribeRequest {
    private Integer subscriptionId;
    private String userName;
    private String userPhoneNumber;

    public UnsubscribeParam toDto() {
        return UnsubscribeParam.builder()
                .subscriptionId(subscriptionId)
                .userInfo(UserInfo.builder()
                        .name(userName)
                        .phoneNumber(userPhoneNumber)
                        .build())
                .build();
    }
}
