package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class NotificationCreateRequest {
    private String name;
    private String phoneNumber;
    private String email;
    private UUID performanceId;
}
