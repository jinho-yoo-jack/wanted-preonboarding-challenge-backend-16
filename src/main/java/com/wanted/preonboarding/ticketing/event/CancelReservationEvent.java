package com.wanted.preonboarding.ticketing.event;

import lombok.Getter;

@Getter
public class CancelReservationEvent {
    private final Long performanceSeatInfoId;

    public CancelReservationEvent(Long performanceSeatInfoId) {
        this.performanceSeatInfoId = performanceSeatInfoId;
    }
}
