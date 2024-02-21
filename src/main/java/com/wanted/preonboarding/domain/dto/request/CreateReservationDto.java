package com.wanted.preonboarding.domain.dto.request;

import java.util.List;

public record CreateReservationDto(
    String userName,
    String phoneNum,
    Long amount,
    String performanceName,
    Integer round,
    String line,
    Integer seat,
    //  telecom, welcome, point, etc ..
    List<String> discountPolicies
) {
}