package com.wanted.preonboarding.ticket.application.reservation.event;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.Getter;
import org.springframework.context.ApplicationEvent;


@Getter
public class ReservationCancelledEvent extends ApplicationEvent {

    private final Reservation reservation;

    public ReservationCancelledEvent(Object source, Reservation reservation) {
        super(source);
        this.reservation = reservation;
    }
}
