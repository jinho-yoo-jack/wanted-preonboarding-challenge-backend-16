package com.wanted.preonboarding.ticket.domain.dto;


import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import lombok.Builder;
import lombok.Data;

import java.util.Date;
import java.util.UUID;

@Data
@Builder
public class SendMessagePerformanceSeatInfoDto {

    private UUID performanceId; //공연ID
    private String performanceName; //공연명
    private int round; //회차
    private Date startDate; //시작일시
    private int gate; //좌석정보
    private char line; //좌석정보
    private int seat; //좌석정보

    public static SendMessagePerformanceSeatInfoDto of(PerformanceSeatInfo entity) {
        return SendMessagePerformanceSeatInfoDto.builder()
                .performanceId(entity.getPerformanceId())
//                .performanceName(entity.getName()) 공연명
                .round(entity.getRound())
                //.startDate() //시작일시
                .gate(entity.getGate())
                .line(entity.getLine())
                .seat(entity.getSeat())
                .build();
    }
}