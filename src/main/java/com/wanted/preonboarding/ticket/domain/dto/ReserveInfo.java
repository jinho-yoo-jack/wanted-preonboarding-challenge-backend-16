package com.wanted.preonboarding.ticket.domain.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class ReserveInfo {

    // 공연 및 전시 정보 + 예약자 정보
    @JsonProperty("performance_id")
    private UUID performanceId;

    // 공연명
    @JsonProperty("performance_name")
    private String performanceName;

    // 예매자 이름
    @JsonProperty("reservation_name")
    private String reservationName;

    // 예매자 번호
    @JsonProperty("reservation_phone_number")
    private String reservationPhoneNumber;

    // 예약; 취소;
    @JsonProperty("reservation_status")
    @JsonIgnore
    private String reservationStatus;

    // 회차
    @JsonProperty("round")
    private int round;

    // 줄
    @JsonProperty("line")
    private char line;

    // 좌석
    @JsonProperty("seat")
    private int seat;
}
