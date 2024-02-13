package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class ReservationCancelRequest {
    private Integer reservationId;

    protected ReservationCancelRequest() {
    }

    public ReservationCancelRequest(Integer reservationId) {
        this.reservationId = reservationId;
    }
}
