package com.wanted.preonboarding.ticket.application.notification

import com.wanted.preonboarding.ticket.domain.PerformanceId
import com.wanted.preonboarding.ticket.domain.UserInfo

interface NotificationPort {
    fun findNotificationTargetUsers(performanceId: PerformanceId): List<UserInfo>

    fun sendNotification(
        userInfo: UserInfo,
        notificationMessage: NotificationMessage,
    )
}
