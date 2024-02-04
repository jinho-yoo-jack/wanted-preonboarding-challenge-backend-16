package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ReservationCancelRequest {
    private int reservationId;
}
