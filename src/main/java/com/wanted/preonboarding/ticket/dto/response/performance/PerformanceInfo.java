package com.wanted.preonboarding.ticket.dto.response.performance;

import com.wanted.preonboarding.ticket.domain.performance.Performance;
import com.wanted.preonboarding.ticket.domain.performance.model.PerformanceType;
import com.wanted.preonboarding.ticket.domain.performance.model.ReserveState;
import java.time.LocalDateTime;
import lombok.Builder;

@Builder
public record PerformanceInfo(
    String name,
    PerformanceType type,
    int round,
    int price,
    LocalDateTime startDate,
    ReserveState isReserved
) {

    public static PerformanceInfo of(final Performance performance) {
        return PerformanceInfo.builder()
            .name(performance.getName())
            .type(performance.getType())
            .round(performance.getRound())
            .price(performance.getPrice())
            .startDate(performance.getStartDate())
            .isReserved(performance.getIsReserve())
            .build();
    }
}
