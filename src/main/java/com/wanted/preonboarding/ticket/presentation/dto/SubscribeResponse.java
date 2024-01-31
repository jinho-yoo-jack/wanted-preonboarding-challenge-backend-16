package com.wanted.preonboarding.ticket.presentation.dto;

import com.wanted.preonboarding.ticket.domain.entity.Subscription;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SubscribeResponse {
    private Integer id;

    public static SubscribeResponse of(Subscription subscribe) {
        return SubscribeResponse.builder().id(subscribe.getId()).build();
    }
}
