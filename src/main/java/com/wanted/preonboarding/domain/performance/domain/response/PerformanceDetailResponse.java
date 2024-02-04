package com.wanted.preonboarding.domain.performance.domain.response;

import java.time.ZonedDateTime;
import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.wanted.preonboarding.domain.performance.domain.dto.PerformanceHallDto;
import com.wanted.preonboarding.domain.performance.domain.entity.Performance;
import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceStatus;
import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = PerformanceDetailResponse.JsonBuilder.class)
@Builder(builderClassName = "JsonBuilder", toBuilder = true, access = AccessLevel.PROTECTED)
public class PerformanceDetailResponse {
    @JsonPOJOBuilder(withPrefix = "")
    protected static class JsonBuilder {}

    private UUID id;

    private String name;
    private PerformanceStatus performanceStatus;
    private PerformanceType performanceType;

    private Long price;
    private Integer round;

    private ZonedDateTime startAt;

    private PerformanceHallDto hall;


    public static PerformanceDetailResponse of(
        Performance performance,
        PerformanceHallDto hall) {

        return PerformanceDetailResponse.builder()
            .id(performance.getId())
            .name(performance.getName())
            .performanceStatus(performance.getPerformanceStatus())
            .performanceType(performance.getPerformanceType())
            .price(performance.getPrice())
            .round(performance.getRound())
            .startAt(performance.getStartAt())
            .hall(hall)
            .build();
    }


}
