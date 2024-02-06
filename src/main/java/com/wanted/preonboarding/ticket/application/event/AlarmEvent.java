package com.wanted.preonboarding.ticket.application.event;

import lombok.Getter;
import org.springframework.context.ApplicationEvent;

@Getter
public class AlarmEvent<T> extends ApplicationEvent {

    private T data;

    public AlarmEvent(T data) {
        super(data);
        this.data = data;
    }
}
