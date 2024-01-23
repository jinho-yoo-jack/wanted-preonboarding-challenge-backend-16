package com.wanted.preonboarding.ticket.application.ticket

import com.wanted.preonboarding.ticket.domain.Performance
import com.wanted.preonboarding.ticket.domain.PerformanceId
import com.wanted.preonboarding.ticket.domain.ReservationId
import com.wanted.preonboarding.ticket.domain.UserInfo

interface PerformancePort {
    fun findPerformance(id: PerformanceId): Performance?

    fun findByReservationInfo(
        userInfo: UserInfo,
        cursor: ReservationId?,
        size: Int,
    ): List<Performance>

    fun update(performance: Performance)
}
