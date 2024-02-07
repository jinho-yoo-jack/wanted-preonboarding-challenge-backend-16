package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class ReadReservationRequest {
    private String reservationName;
    private String reservationPhoneNumber;

    public static ReadReservationRequest of(String reservationName, String reservationPhoneNumber) {
        return ReadReservationRequest.builder()
                .reservationName(reservationName)
                .reservationPhoneNumber(reservationPhoneNumber)
                .build();
    }
}
