package com.wanted.preonboarding.ticket.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class NotificationCreateResponse {
    private Integer id;
    private String name;
    private UUID performanceId;
}
