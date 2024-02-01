package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class SubscriberPerformanceRequestDto {
    // 공연 및 전시 정보 + 예약자 정보
    private String receiver;
    private UUID performanceId;

}
