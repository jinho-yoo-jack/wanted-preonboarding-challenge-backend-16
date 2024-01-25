package com.wanted.preonboarding.base.event;

import java.util.UUID;

import org.springframework.context.ApplicationEvent;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;

import lombok.Getter;

@Getter
public class EventCancleTicket extends ApplicationEvent {
	private Performance performance;
	private PerformanceSeatInfo performanceSeatInfo;

	public EventCancleTicket(Object source, Performance performance, PerformanceSeatInfo performanceSeatInfo) {
		super(source);
		this.performance = performance;
		this.performanceSeatInfo = performanceSeatInfo;
	}
}
