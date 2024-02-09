package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class SeatInfo {
    private int id;
    private UUID performanceId;
    private int round;
    private int gate;
    private char line;
    private int seat;
    private String isReserve;

    public static SeatInfo of(PerformanceSeatInfo entity){
        return SeatInfo.builder()
            .id(entity.getId())
            .performanceId(entity.getPerformance().getId())
            .round(entity.getPerformance().getRound())
            .gate(entity.getGate())
            .line(entity.getLine())
            .seat(entity.getSeat())
            .isReserve(entity.getIsReserve())
            .build();
    }
}
