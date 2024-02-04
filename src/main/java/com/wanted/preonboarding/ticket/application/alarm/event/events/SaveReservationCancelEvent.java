package com.wanted.preonboarding.ticket.application.alarm.event.events;

import com.wanted.preonboarding.ticket.domain.dto.request.ReserverInfoRequest;
import lombok.Getter;

@Getter
public class SaveReservationCancelEvent {
    private String performanceId;
    private ReserverInfoRequest reserverInfoRequest;
    public SaveReservationCancelEvent(String performanceId, ReserverInfoRequest reserverInfoRequest) {
        this.performanceId = performanceId;
        this.reserverInfoRequest = reserverInfoRequest;
    }
}
