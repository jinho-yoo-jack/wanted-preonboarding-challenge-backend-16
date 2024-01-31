package com.wanted.preonboarding.performance.framwork.infrastructure.eventAdapter;

import com.wanted.preonboarding.performance.application.PerformCancelEventService;

import com.wanted.preonboarding.reservation.domain.event.ReservationCancelEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReservationCancelEventListener {

	private final PerformCancelEventService performCancelEventService;

	@Async
	@EventListener
	public void listenCancelEvent(ReservationCancelEvent event) {
		performCancelEventService.canceled(event);
	}

}
