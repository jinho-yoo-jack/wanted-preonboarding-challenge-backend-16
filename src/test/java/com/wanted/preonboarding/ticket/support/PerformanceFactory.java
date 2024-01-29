package com.wanted.preonboarding.ticket.support;

import com.wanted.preonboarding.ticket.domain.entity.Performance;

import java.time.LocalDateTime;
import java.util.UUID;

public interface PerformanceFactory {

    static Performance create(UUID performanceId) {
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

    static Performance create() {
        return Performance.builder()
                .id(UUID.randomUUID())
                .name("영웅")
                .price(10000)
                .round(1)
                .type(1)
                .startDate(LocalDateTime.now())
                .isReserve("able")
                .build();
    }

    static Performance changeReservationState(Performance performance, String isReserved) {
        return Performance.builder()
                .id(performance.getId())
                .name(performance.getName())
                .price(performance.getPrice().getAmount().intValue())
                .type(performance.getType())
                .round(performance.getRound())
                .startDate(performance.getStartDate())
                .isReserve(isReserved)
                .build();
    }
}
