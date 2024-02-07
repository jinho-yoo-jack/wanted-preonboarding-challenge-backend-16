package com.wanted.preonboarding.ticket.domain.dto;


import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.UUID;

@Getter @Setter
@Builder
public class SendMessagePerformanceSeat {

    private UUID performanceId; //공연ID
    private String performanceName; //공연명
    private int round; //회차
    private Date startDate; //시작일시
    private int gate; //좌석정보
    private char line; //좌석정보
    private int seat; //좌석정보

    public static SendMessagePerformanceSeat of(Performance performance, PerformanceSeatInfo performanceSeatInfo) {
        return SendMessagePerformanceSeat.builder()
                .performanceId(performanceSeatInfo.getPerformanceId())
                .performanceName(performance.getName())
                .round(performanceSeatInfo.getRound())
                .startDate(performance.getStart_date())
                .gate(performanceSeatInfo.getGate())
                .line(performanceSeatInfo.getLine())
                .seat(performanceSeatInfo.getSeat())
                .build();
    }
}
