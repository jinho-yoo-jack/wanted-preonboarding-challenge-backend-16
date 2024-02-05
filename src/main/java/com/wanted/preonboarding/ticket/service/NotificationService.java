package com.wanted.preonboarding.ticket.service;

import java.util.List;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionalEventListener;

import com.wanted.preonboarding.ticket.domain.event.PerformanceCancelEvent;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.repository.ReservationRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final ReservationRepository reservationRepository;

    @Async
    @TransactionalEventListener(classes = {PerformanceCancelEvent.class})
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void createCancelPerformanceNotification(final PerformanceCancelEvent event) {
        final Notification notification = Notification.builder()
                .performance(event.performance())
                .build();

        notificationRepository.save(notification);

        sendNotificationsToCanceledReservations(event);
    }

    private void sendNotificationsToCanceledReservations(final PerformanceCancelEvent event) {
        final List<Reservation> reservations =
                reservationRepository.findAllByPerformanceAndRound(event.performance(), event.performance().getRound());
        for (final Reservation reservation : reservations) {
            log.info("{}님 공연이 취소되었습니다.\n"
                            + " 공연명: {}, 회차: {}, 좌석: {}열 {}번",
                    reservation.getName(),
                    reservation.getPerformance().getName(), reservation.getRound(), reservation.getLine() , reservation.getSeat());
        }
    }
}
