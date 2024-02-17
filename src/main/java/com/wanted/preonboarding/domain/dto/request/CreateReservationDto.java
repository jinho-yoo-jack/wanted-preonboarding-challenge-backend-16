package com.wanted.preonboarding.domain.dto.request;

import java.util.List;
import java.util.UUID;

public record CreateReservationDto(
    String userName,
    String phoneNum,
    Long amount,
    UUID performanceName,
    Integer round,
    String line,
    Integer seat,
    //  telecom, welcome, point, etc ..
    List<String> discountPolicies
) {
}