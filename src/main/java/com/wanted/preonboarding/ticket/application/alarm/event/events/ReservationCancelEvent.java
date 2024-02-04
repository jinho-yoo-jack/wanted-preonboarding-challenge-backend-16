package com.wanted.preonboarding.ticket.application.alarm.event.events;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.entity.SeatInfo;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class ReservationCancelEvent {
    private String performanceId;
    private SeatInfo seatInfo;
    public ReservationCancelEvent(Performance performance, Reservation reservation)
    {
        this.performanceId = performance.getId().toString();
        this.seatInfo = reservation.getPerformanceSeatInfo().getSeatInfo();
    }
}