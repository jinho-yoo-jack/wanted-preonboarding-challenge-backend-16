package com.wanted.preonboarding.ticket.domain.eventListener;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.base.event.EventCancleTicket;
import com.wanted.preonboarding.ticket.application.NotificationService;

import jakarta.mail.MessagingException;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class NotificationEventListener {

	private final NotificationService notificationService;

	@EventListener
	@Transactional
	public void listen(EventCancleTicket event) throws MessagingException {
		notificationService.whenCancleTicketEvent(event.getPerformance(), event.getPerformanceSeatInfo());
	}
}
