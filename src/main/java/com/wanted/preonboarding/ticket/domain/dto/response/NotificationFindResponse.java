package com.wanted.preonboarding.ticket.domain.dto.response;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class NotificationFindResponse {
    private String name;
    private String phoneNumber;
    private String email;
    private UUID performanceId;
}
