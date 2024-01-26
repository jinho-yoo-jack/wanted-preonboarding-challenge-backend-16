package com.wanted.preonboarding.ticket.support;

import com.wanted.preonboarding.ticket.domain.entity.Performance;

import java.time.LocalDateTime;
import java.util.UUID;

public final class PerformanceFactory {

    private PerformanceFactory() {

    }

    public static Performance create(UUID performanceId) {
        return Performance.builder()
                .id(performanceId)
                .name("영웅")
                .price(10000)
                .round(1)
                .type(1)
                .startDate(LocalDateTime.now())
                .isReserve("able")
                .build();
    }
}
