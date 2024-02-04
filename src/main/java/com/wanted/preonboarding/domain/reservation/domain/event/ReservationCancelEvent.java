package com.wanted.preonboarding.domain.reservation.domain.event;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import lombok.Getter;

@Getter
public class ReservationCancelEvent extends ApplicationEvent {

    private final UUID performanceId;
    private final Long hallSeatId;

    public ReservationCancelEvent(
        Object source,
        UUID performanceId,
        Long hallSeatId) {

        super(source);
        this.performanceId = performanceId;
        this.hallSeatId = hallSeatId;
    }
}
