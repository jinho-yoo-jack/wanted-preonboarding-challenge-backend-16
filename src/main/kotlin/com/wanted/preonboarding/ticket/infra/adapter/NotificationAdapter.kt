package com.wanted.preonboarding.ticket.infra.adapter

import com.wanted.preonboarding.ticket.application.notification.NotificationMessage
import com.wanted.preonboarding.ticket.application.notification.NotificationPort
import com.wanted.preonboarding.ticket.domain.PerformanceId
import com.wanted.preonboarding.ticket.domain.UserInfo
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

/**
 * 실제로 Notification이 발송되는 로직을 의도적으로 구현하지 않았음.
 */
@Component
class NotificationAdapter : NotificationPort {
    private val logger = LoggerFactory.getLogger(javaClass)

    override fun findNotificationTargetUsers(performanceId: PerformanceId): List<UserInfo> {
        return listOf(
            UserInfo("홍길동", "01096850887"),
            UserInfo("김철수", "01012345678"),
        )
    }

    override fun sendNotification(
        userInfo: UserInfo,
        notificationMessage: NotificationMessage,
    ) {
        logger.info("send notification to $userInfo: $notificationMessage")
    }
}
