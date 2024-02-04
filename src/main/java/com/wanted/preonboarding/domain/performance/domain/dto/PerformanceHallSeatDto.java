package com.wanted.preonboarding.domain.performance.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.wanted.preonboarding.domain.performance.domain.entity.PerformanceHallSeat;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = PerformanceHallSeatDto.JsonBuilder.class)
@Builder(builderClassName = "JsonBuilder", toBuilder = true, access = AccessLevel.PROTECTED)
public class PerformanceHallSeatDto {
    @JsonPOJOBuilder(withPrefix = "")
    protected static class JsonBuilder {}

    private Long id;

    private int gate;
    private char line;
    private int seat;


    public static PerformanceHallSeatDto of(PerformanceHallSeat hallSeat) {

        return PerformanceHallSeatDto.builder()
            .id(hallSeat.getId())
            .gate(hallSeat.getGate())
            .line(hallSeat.getLine())
            .seat(hallSeat.getSeat())
            .build();
    }

}
