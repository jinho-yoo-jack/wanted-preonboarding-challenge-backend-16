package com.wanted.preonboarding.ticket.application.service;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.event.ReservationCancelledEvent;
import com.wanted.preonboarding.ticket.application.exception.EntityNotFoundException;
import com.wanted.preonboarding.ticket.application.repository.NotificationRepository;
import com.wanted.preonboarding.ticket.application.repository.PerformanceRepository;
import com.wanted.preonboarding.ticket.domain.dto.RequestNotification;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.UUID;

import static com.wanted.preonboarding.core.domain.response.ResponseHandler.MESSAGE_SUCCESS;
import static com.wanted.preonboarding.core.domain.response.ResponseHandler.createResponse;
import static com.wanted.preonboarding.ticket.application.exception.ExceptionStatus.NOT_FOUND_INFO;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final PerformanceRepository performanceRepository;

    @Transactional
    public ResponseEntity<ResponseHandler<Void>> setNotification(
            RequestNotification requestNotification
    ) {
        log.info("--- Set Notification ---");
        UUID id = requestNotification.performanceId();
        int round = requestNotification.round();
        Performance performance = performanceRepository.findByIdAndRound(id, round)
                .orElseThrow(() -> new EntityNotFoundException(NOT_FOUND_INFO));

        notificationRepository.save(Notification.of(requestNotification, performance));
        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, null);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservationCancelledEvent(ReservationCancelledEvent event) {
        log.info("--- Reservation Cancelled Event ---");
        Reservation reservation = event.getReservation();
        Performance performance = reservation.getPerformanceSeatInfo().getPerformance();
        List<Notification> notificationList = notificationRepository.findAllByPerformance(performance);

        notificationList.forEach(notification -> {
                sendNotification(notification, reservation);
                notification.markAsSent();
        });
    }

    private void sendNotification(Notification notification, Reservation reservation) {
        log.info("[알림 발송] {} 공연/전시의 {} 회차 {}열 {} 예약이 취소되었습니다. - 알림 수신자 이메일: {}",
                notification.getPerformance().getName(),
                notification.getRound(),
                reservation.getLine(),
                reservation.getSeat(),
                notification.getEmail());
    }

}
