package com.wanted.preonboarding.ticket.presentation.request;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ReadReservationRequest {
    private String reservationName;
    private String reservationPhoneNumber;
}
