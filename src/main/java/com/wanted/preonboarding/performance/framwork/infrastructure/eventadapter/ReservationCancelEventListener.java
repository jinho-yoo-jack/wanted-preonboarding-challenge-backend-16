package com.wanted.preonboarding.performance.framwork.infrastructure.eventadapter;

import com.wanted.preonboarding.performance.application.PerformCancelEventService;
import com.wanted.preonboarding.reservation.domain.event.ReservationCancelEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ReservationCancelEventListener {

	private final PerformCancelEventService performCancelEventService;

	@Async
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void listenCancelEvent(ReservationCancelEvent event) {
		performCancelEventService.canceled(event);
	}

}
