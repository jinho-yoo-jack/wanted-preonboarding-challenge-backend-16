package com.wanted.preonboarding.ticket.support;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;

import java.util.UUID;

public final class PerformanceSeatFactory {

    private PerformanceSeatFactory() {

    }

    public static PerformanceSeatInfo create() {
        return PerformanceSeatInfo.builder()
                .performanceId(UUID.randomUUID())
                .seat(1)
                .gate(1)
                .line('a')
                .round(1)
                .isReserve(PerformanceSeatInfo.ENABLE)
                .build();
    }

    public static PerformanceSeatInfo changeReservationStatus(PerformanceSeatInfo performanceSeatInfo,
                                                              String isReserve)
    {
        return PerformanceSeatInfo.builder()
                .performanceId(performanceSeatInfo.getPerformanceId())
                .seat(performanceSeatInfo.getSeat())
                .gate(performanceSeatInfo.getGate())
                .line(performanceSeatInfo.getLine())
                .round(performanceSeatInfo.getRound())
                .isReserve(isReserve)
                .build();
    }
}
