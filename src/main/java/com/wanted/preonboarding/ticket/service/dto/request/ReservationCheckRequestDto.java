package com.wanted.preonboarding.ticket.service.dto.request;

import lombok.Builder;

@Builder
public record ReservationCheckRequestDto(String reservationName, String reservationPhoneNumber) {
}
