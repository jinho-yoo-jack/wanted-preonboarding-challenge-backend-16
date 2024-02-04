package com.wanted.preonboarding.ticket.application.dto;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceDetailDto {
    private UUID performanceId;
    private String name;
    private Integer round;
    private PerformanceType type;
    private LocalDateTime startDate;
    private Boolean isReservable;
    private List<PerformanceSeatInfoDto> seats;

    public static PerformanceDetailDto of(Performance entity) {
        return PerformanceDetailDto.builder()
                .performanceId(entity.getId())
                .name(entity.getName())
                .round(entity.getRound())
                .type(entity.getType())
                .startDate(entity.getStartDate())
                .isReservable(entity.getIsReservable())
                .seats(entity.getSeatInfos().stream().map(PerformanceSeatInfoDto::of).collect(Collectors.toList()))
                .build();
    }
}
