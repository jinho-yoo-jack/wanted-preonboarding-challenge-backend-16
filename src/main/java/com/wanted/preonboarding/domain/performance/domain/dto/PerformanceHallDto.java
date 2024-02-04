package com.wanted.preonboarding.domain.performance.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.wanted.preonboarding.domain.performance.domain.entity.PerformanceHall;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = PerformanceHallDto.JsonBuilder.class)
@Builder(builderClassName = "JsonBuilder", toBuilder = true, access = AccessLevel.PROTECTED)
public class PerformanceHallDto {
    @JsonPOJOBuilder(withPrefix = "")
    protected static class JsonBuilder {}

    private Long id;

    private String name;


    public static PerformanceHallDto of(PerformanceHall hall) {

        return PerformanceHallDto.builder()
            .id(hall.getId())
            .name(hall.getName())
            .build();
    }

}
