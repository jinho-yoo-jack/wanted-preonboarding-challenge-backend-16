package com.wanted.preonboarding.domain.reservation.domain.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.wanted.preonboarding.domain.common.domain.dto.PageDto;
import com.wanted.preonboarding.domain.performance.domain.dto.PerformanceDto;
import com.wanted.preonboarding.domain.reservation.domain.dto.ReservationDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = ReservationPageResponse.JsonBuilder.class)
@Builder(builderClassName = "JsonBuilder", toBuilder = true, access = AccessLevel.PROTECTED)
public class ReservationPageResponse {
    @JsonPOJOBuilder(withPrefix = "")
    protected static class JsonBuilder {}

    private PageDto<ReservationDto> reservationPage;



    public static ReservationPageResponse of(
        PageDto<ReservationDto> reservationPage) {

        return ReservationPageResponse.builder()
            .reservationPage(reservationPage)
            .build();
    }

}
