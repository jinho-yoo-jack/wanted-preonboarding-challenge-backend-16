package com.wanted.preonboarding.ticket.application.notification.service;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.annotation.ExecutionTimer;
import com.wanted.preonboarding.ticket.application.common.service.MailService;
import com.wanted.preonboarding.ticket.application.notification.repository.NotificationRepository;
import com.wanted.preonboarding.ticket.application.performance.service.PerformanceService;
import com.wanted.preonboarding.ticket.application.reservation.event.ReservationCancelledEvent;
import com.wanted.preonboarding.ticket.domain.dto.request.RequestNotification;
import com.wanted.preonboarding.ticket.domain.dto.request.SendNotification;
import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.event.TransactionPhase;
import org.springframework.transaction.event.TransactionalEventListener;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.wanted.preonboarding.core.domain.response.ResponseHandler.MESSAGE_SUCCESS;
import static com.wanted.preonboarding.core.domain.response.ResponseHandler.createResponse;

@Slf4j
@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository notificationRepository;
    private final PerformanceService performanceService;
    private final MailService mailService;

    @Transactional
    public void setNotification(
            RequestNotification requestNotification
    ) {
        log.info("--- Set Notification ---");
        UUID id = requestNotification.performanceId();
        int round = requestNotification.round();
        Performance performance = performanceService.getPerformanceEntity(id, round);

        notificationRepository.save(Notification.of(requestNotification, performance));
    }

    @ExecutionTimer
    @Async("asyncExecutor")
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    public void handleReservationCancelledEvent(ReservationCancelledEvent event) {
        log.info("--- Reservation Cancelled Event ---");
        Reservation reservation = event.getReservation();
        Performance performance = reservation.getPerformanceSeatInfo().getPerformance();
        List<Notification> notificationList = notificationRepository.findAllByPerformance(performance);

        CompletableFuture<Boolean> result = sendNotificationMail(notificationList, reservation);
        markAsSentIfMailSentSuccess(notificationList, result);
    }

    // ========== PRIVATE METHODS ========== //

    private CompletableFuture<Boolean> sendNotificationMail(
            List<Notification> notificationList,
            Reservation reservation
    ) {
        SendNotification sendNotification = SendNotification.of(notificationList, reservation);
        return mailService.sendNotificationMail(sendNotification);
    }

    private void markAsSentIfMailSentSuccess(List<Notification> notificationList, CompletableFuture<Boolean> result) {
        result.thenAccept(isSuccess -> {
            if (Boolean.TRUE.equals(isSuccess)) {
                notificationList.forEach(Notification::markAsSent);
            }
        });
    }

}
