package com.wanted.preonboarding.ticket.application.notification

import com.wanted.preonboarding.core.exception.ApplicationException
import com.wanted.preonboarding.ticket.application.ticket.PerformancePort
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class ReservationEventListener(
    private val notificationPort: NotificationPort,
    private val performancePort: PerformancePort,
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun handleCancelEvent(reservationCancelEvent: ReservationCancelEvent) {
        val performance =
            performancePort.findPerformance(reservationCancelEvent.performanceId)
                ?: throw ApplicationException.NotFoundException("존재하지 않는 공연입니다.")

        val targetUsers = notificationPort.findNotificationTargetUsers(reservationCancelEvent.performanceId)
        val notificationMessage = NotificationMessage.from(performance, reservationCancelEvent.seatInfo)
        targetUsers.forEach {
            notificationPort.sendNotification(it, notificationMessage)
        }
    }
}
