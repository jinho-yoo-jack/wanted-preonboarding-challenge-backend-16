package com.wanted.preonboarding.domain.reservation.domain.request;

import java.util.UUID;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = ReservationCreateRequest.JsonBuilder.class)
@Builder(builderClassName = "JsonBuilder", toBuilder = true, access = AccessLevel.PROTECTED)
public class ReservationCreateRequest {
	@JsonPOJOBuilder(withPrefix = "")
	protected static class JsonBuilder { }

	// 공연 정보
	UUID performanceId;
	// 좌석 정보
	Long hallSeatId;


	// 예약자 정보
	String name;
	String phoneNumber;

	// TODO 결제 정보
	Long money;

}
