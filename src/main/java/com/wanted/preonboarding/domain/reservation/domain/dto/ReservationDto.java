package com.wanted.preonboarding.domain.reservation.domain.dto;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.wanted.preonboarding.domain.performance.domain.dto.PerformanceDto;
import com.wanted.preonboarding.domain.performance.domain.dto.PerformanceHallDto;
import com.wanted.preonboarding.domain.performance.domain.dto.PerformanceHallSeatDto;
import com.wanted.preonboarding.domain.reservation.domain.entity.Reservation;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = ReservationDto.JsonBuilder.class)
@Builder(builderClassName = "JsonBuilder", toBuilder = true, access = AccessLevel.PROTECTED)
public class ReservationDto {
    @JsonPOJOBuilder(withPrefix = "")
    protected static class JsonBuilder {}

    private Long id;

    private PerformanceDto performance;
    private PerformanceHallDto hall;
    private PerformanceHallSeatDto hallSeat;

    private String name;
    private String phoneNumber;

    public static ReservationDto of(
        Reservation reservation,
        PerformanceDto performance,
        PerformanceHallDto hall,
        PerformanceHallSeatDto hallSeat) {

        return ReservationDto.builder()
            .id(reservation.getId())

            .performance(performance)
            .hall(hall)
            .hallSeat(hallSeat)

            .name(reservation.getName())
            .phoneNumber(reservation.getPhoneNumber())

            .build();
    }


}
