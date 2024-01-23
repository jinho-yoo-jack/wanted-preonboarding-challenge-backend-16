package com.wanted.preonboarding.ticket.application.notification

import com.wanted.preonboarding.ticket.application.ticket.PerformancePort
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener

@Component
class NotificationEventListener(
    private val notificationPort: NotificationPort,
    private val performancePort: PerformancePort,
) {
    @Async
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT)
    fun sendNotification(notificationEvent: NotificationEvent) {
        val performance =
            performancePort.findPerformance(notificationEvent.performanceId)
                ?: throw RuntimeException("존재하지 않는 공연입니다.")

        val targetUsers = notificationPort.findNotificationTargetUsers(notificationEvent.performanceId)
        val notificationMessage = NotificationMessage.from(performance, notificationEvent.seatInfo)
        targetUsers.forEach {
            notificationPort.sendNotification(it, notificationMessage)
        }
    }
}
