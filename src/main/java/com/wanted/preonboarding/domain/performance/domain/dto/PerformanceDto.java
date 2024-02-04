package com.wanted.preonboarding.domain.performance.domain.dto;

import java.time.ZonedDateTime;
import java.util.UUID;


import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.wanted.preonboarding.domain.performance.domain.entity.Performance;
import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceStatus;
import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceType;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = PerformanceDto.JsonBuilder.class)
@Builder(builderClassName = "JsonBuilder", toBuilder = true, access = AccessLevel.PROTECTED)
public class PerformanceDto {
    @JsonPOJOBuilder(withPrefix = "")
    protected static class JsonBuilder {}

    private UUID id;

    private String name;
    private PerformanceStatus performanceStatus;

    private Long price;
    private Integer round;
    private PerformanceType performanceType;

    private ZonedDateTime startAt;


    public static PerformanceDto of(Performance performance) {

        return PerformanceDto.builder()
            .id(performance.getId())
            .name(performance.getName())
            .performanceStatus(performance.getPerformanceStatus())
            .price(performance.getPrice())
            .round(performance.getRound())
            .performanceType(performance.getPerformanceType())
            .startAt(performance.getStartAt())

            .build();
    }


}
