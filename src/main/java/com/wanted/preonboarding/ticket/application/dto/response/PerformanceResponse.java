package com.wanted.preonboarding.ticket.application.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record PerformanceResponse(
        String performanceName,

        int round,

        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Seoul")
        LocalDateTime performanceDate,

        String isReserve
) {

    public static PerformanceResponse from(Performance performance) {
        return PerformanceResponse.builder()
                .performanceName(performance.getName())
                .round(performance.getRound())
                .performanceDate(performance.getStartDate())
                .isReserve(performance.getIsReserve())
                .build();
    }
}
