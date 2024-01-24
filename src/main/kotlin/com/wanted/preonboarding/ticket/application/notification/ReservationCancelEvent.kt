package com.wanted.preonboarding.ticket.application.notification

import com.wanted.preonboarding.ticket.domain.PerformanceId
import com.wanted.preonboarding.ticket.domain.SeatInfo

data class ReservationCancelEvent(
    val performanceId: PerformanceId,
    val seatInfo: SeatInfo,
)
