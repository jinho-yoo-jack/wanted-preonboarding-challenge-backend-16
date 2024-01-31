package com.wanted.preonboarding.ticket.application.dto;

import com.wanted.preonboarding.ticket.domain.entity.SeatInfo;
import com.wanted.preonboarding.ticket.domain.entity.UserInfo;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class SubscribeParam {
    private UUID performanceId;
    private UserInfo userInfo;
}
