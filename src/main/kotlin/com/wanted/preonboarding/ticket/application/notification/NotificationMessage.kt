package com.wanted.preonboarding.ticket.application.notification

import com.wanted.preonboarding.ticket.domain.Performance
import com.wanted.preonboarding.ticket.domain.SeatInfo
import java.time.LocalDateTime
import java.util.UUID

data class NotificationMessage(
    val performanceId: UUID,
    val name: String,
    val round: Int,
    val startDate: LocalDateTime,
    val seatInfo: SeatInfo,
) {
    companion object {
        fun from(
            performance: Performance,
            seatInfo: SeatInfo,
        ): NotificationMessage {
            return NotificationMessage(
                performanceId = performance.id.value,
                name = performance.name,
                round = performance.round,
                startDate = performance.startDate,
                seatInfo = seatInfo,
            )
        }
    }
}
