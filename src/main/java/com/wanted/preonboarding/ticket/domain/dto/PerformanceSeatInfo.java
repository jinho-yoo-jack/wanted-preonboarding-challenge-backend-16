package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeat;
import lombok.Builder;
import lombok.Data;

import java.util.UUID;

@Data
@Builder
public class PerformanceSeatInfo {
    private Integer id;
    private UUID performanceId;
    private int round;
    private int gate;
    private char line;
    private int seat;
    private String isReserve;

    public static PerformanceSeatInfo of(PerformanceSeat entity) {
        return PerformanceSeatInfo.builder()
                .id(entity.getId())
                .performanceId(entity.getPerformanceId())
                .round(entity.getRound())
                .gate(entity.getGate())
                .line(entity.getLine())
                .seat(entity.getSeat())
                .isReserve(entity.getIsReserve())
                .build();
    }
}
