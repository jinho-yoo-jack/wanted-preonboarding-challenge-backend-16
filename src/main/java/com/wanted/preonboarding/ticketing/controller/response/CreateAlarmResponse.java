package com.wanted.preonboarding.ticketing.controller.response;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class CreateAlarmResponse {
    private final Long alarmId;
    private final UUID performanceId;
    private final String customerName;
    private final String phoneNumber;
    private final LocalDateTime startedTime;
}
