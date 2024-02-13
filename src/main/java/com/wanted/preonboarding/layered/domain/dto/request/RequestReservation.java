package com.wanted.preonboarding.layered.domain.dto.request;

import lombok.*;

import java.util.*;

@Getter
@Setter
@Builder
@RequiredArgsConstructor
public class RequestReservation {
    private final UUID performanceId;
    private final String performanceName;
    private final int fixedPrice;
    private final int round;
    private final char line;
    private final int seat;
    private final String reservationName;
    private final String reservationPhoneNumber;
    private final int depositAmount;
    private final String reservationStatus; // 예약; 취소;
    private final List<String> appliedPolicies; // ['telecome', 'new_member', 'okcashback', 'happy_point']
}
