package com.wanted.preonboarding.core.code;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum ReservationStatus {
    APPLY("신청"),
    CANCEL("취소")
    ;

    private final String description;
}
