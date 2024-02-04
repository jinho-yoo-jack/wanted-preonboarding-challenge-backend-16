package com.wanted.preonboarding.ticket.application.alarm.event.publisher;

import com.wanted.preonboarding.ticket.application.alarm.event.events.ReservationCancelEvent;
import com.wanted.preonboarding.ticket.application.alarm.event.events.SaveReservationCancelEvent;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReserverInfoRequest;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ReservationEventPublisher {
    private final ApplicationEventPublisher publisher;
    public void publishReservationCancelEvent(Performance performance, Reservation reservation)
    {
        publisher.publishEvent(new ReservationCancelEvent(performance, reservation));
    }
    public void publishSaveReservationCancelEvent(ReservationRequest request)
    {
        publisher.publishEvent(new SaveReservationCancelEvent(request.getPerformanceId(), request.getReserverInfoRequest()));
    }
}
