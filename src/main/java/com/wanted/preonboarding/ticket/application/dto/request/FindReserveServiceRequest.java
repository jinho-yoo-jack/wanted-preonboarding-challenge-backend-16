package com.wanted.preonboarding.ticket.application.dto.request;

import lombok.Builder;

@Builder
public record FindReserveServiceRequest(
        String reservationName,
        String reservationPhoneNumber
) {
}
