package com.wanted.preonboarding.domain.reservation.domain.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.wanted.preonboarding.domain.reservation.domain.dto.ReservationDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = ReservationCreateResponse.JsonBuilder.class)
@Builder(builderClassName = "JsonBuilder", toBuilder = true, access = AccessLevel.PROTECTED)
public class ReservationCreateResponse {
	@JsonPOJOBuilder(withPrefix = "")
	protected static class JsonBuilder {
	}

	private ReservationDto reservation;

	public static ReservationCreateResponse of(
		ReservationDto reservation) {

		return ReservationCreateResponse.builder()
			.reservation(reservation)
			.build();
	}

}
