package com.wanted.preonboarding.base.event;

import org.springframework.context.ApplicationEvent;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;

import lombok.Getter;

@Getter
public class EventCancleTicket extends ApplicationEvent {
	private final Performance performance;
	private final PerformanceSeatInfo performanceSeatInfo;

	public EventCancleTicket(Object source, Performance performance, PerformanceSeatInfo performanceSeatInfo) {
		super(source);
		this.performance = performance;
		this.performanceSeatInfo = performanceSeatInfo;
	}
}
