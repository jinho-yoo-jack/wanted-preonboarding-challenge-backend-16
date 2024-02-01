package com.wanted.preonboarding.ticket.domain.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter
@Setter
public class ResponseAlarmDto {
    // 공연 및 전시 정보 + 예약자 정보
    private String receiver;
    private UUID performanceId;
    private String performanceName;
    private Date startDate;

    @Builder
    public ResponseAlarmDto(String receiver, UUID performanceId, String performanceName, Date startDate, int round, int gate, char line, int seat) {
        this.receiver = receiver;
        this.performanceId = performanceId;
        this.performanceName = performanceName;
        this.startDate = startDate;
    }
}
