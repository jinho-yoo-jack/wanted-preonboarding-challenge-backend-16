package com.wanted.preonboarding.ticket.domain.dto;

import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record RequestReservation(
        @Pattern(regexp = "^[가-힣]{2,4}$")
        String name,
        @Pattern(regexp = "^01(?:0|1|[6-9])-?(?:\\d{3}|\\d{4})-?\\d{4}$", message = "휴대전화 번호만 입력해주세요.")
        String phoneNumber,
        @Positive
        Integer balance,
        UUID performanceId,
        @Positive
        Integer round,
        @Pattern(regexp = "^[A-Z]$")
        Character line,
        @Positive
        Integer seat
) {


}
