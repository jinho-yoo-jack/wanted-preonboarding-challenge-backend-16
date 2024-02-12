package com.wanted.preonboarding.ticket.application.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceType;
import lombok.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceDto {
    private UUID performanceId;
    private String name;
    private Integer price;
    private Integer round;
    private PerformanceType type;
    private LocalDateTime startDate;
    private Boolean isReservable;

    public static PerformanceDto of(final Performance entity) {
        return PerformanceDto.builder()
                .performanceId(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .round(entity.getRound())
                .type(entity.getType())
                .startDate(entity.getStartDate())
                .isReservable(entity.getIsReservable())
                .build();
    }
}
