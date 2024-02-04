package com.wanted.preonboarding.domain.reservation.listener;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import com.wanted.preonboarding.domain.reservation.domain.event.ReservationCancelEvent;
import com.wanted.preonboarding.domain.reservation.service.ReservationNotificationService;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
@RequiredArgsConstructor
public class ReservationCancelEventListener {

	private final ReservationNotificationService reservationNotificationService;

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	@TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
	public void sendNotification(ReservationCancelEvent event) {

		this.reservationNotificationService.sendReservationNotification(
			event.getPerformanceId(),
			event.getHallSeatId());
	}
}
