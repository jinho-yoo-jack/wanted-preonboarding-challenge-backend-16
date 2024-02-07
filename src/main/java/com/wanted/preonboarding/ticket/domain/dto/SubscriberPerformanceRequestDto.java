package com.wanted.preonboarding.ticket.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
public class SubscriberPerformanceRequestDto {
    // 공연 및 전시 정보 + 예약자 정보
    private UUID performanceId;
    private String receiver;

}
