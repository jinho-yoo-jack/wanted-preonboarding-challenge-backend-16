package com.wanted.preonboarding.ticket.application.event;

import com.wanted.preonboarding.ticket.application.event.dto.AlarmInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AlarmEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishAlarm(AlarmInfo alarmInfo) {
        applicationEventPublisher.publishEvent(new AlarmEvent<>(alarmInfo));
    }
}
