package com.wanted.preonboarding.ticket.application.dto;


import com.wanted.preonboarding.user.User;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AwaitInfo(
        UUID performanceId,
        String name,
        String phoneNumber
) {

    public static AwaitInfo of(UUID performanceId, User user) {
        return AwaitInfo.builder()
                .performanceId(performanceId)
                .name(user.getName())
                .phoneNumber(user.getPhoneNumber())
                .build();
    }
}
