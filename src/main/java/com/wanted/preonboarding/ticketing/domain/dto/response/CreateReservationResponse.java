package com.wanted.preonboarding.ticketing.domain.dto.response;

import com.wanted.preonboarding.ticketing.domain.dto.Discount;
import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Builder
@Getter
public class CreateReservationResponse {
    private String performanceName;
    private UUID performanceId;
    private int round;
    private int gate;
    private String line;
    private int seat;
    private Discount discount;
    private String reservationName;
    private String phoneNumber;
}
