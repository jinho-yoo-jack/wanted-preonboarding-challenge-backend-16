package com.wanted.preonboarding.ticket.application.alarm.event.handler;

import com.wanted.preonboarding.ticket.application.alarm.AlarmService;
import com.wanted.preonboarding.ticket.application.alarm.event.events.ReservationCancelEvent;
import com.wanted.preonboarding.ticket.application.alarm.event.events.SaveReservationCancelEvent;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PerformanceEventHandler {
    private final ApplicationEventPublisher publisher;
    private final AlarmService alarmService;
    @Async
    @EventListener
    public void handlePerformanceCancelEvent(ReservationCancelEvent reservationCancelEvent)
    {
        alarmService.sendCancelAlarm(reservationCancelEvent.getPerformanceId(), reservationCancelEvent.getSeatInfo());
    }
    @Async
    @EventListener
    public void handleSaveReservationCancelEvent(SaveReservationCancelEvent saveReservationCancelEvent)
    {
        alarmService.registerCancelAlarm(saveReservationCancelEvent.getPerformanceId(), saveReservationCancelEvent.getReserverInfoRequest());
    }
}