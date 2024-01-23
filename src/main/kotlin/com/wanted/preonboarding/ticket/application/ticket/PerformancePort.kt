package com.wanted.preonboarding.ticket.application.ticket

import com.wanted.preonboarding.core.CursorResult
import com.wanted.preonboarding.ticket.domain.Performance
import com.wanted.preonboarding.ticket.domain.PerformanceId

interface PerformancePort {
    fun findPerformance(id: PerformanceId): Performance?

    fun update(performance: Performance)

    fun findAllPerformanceByReserveAvailable(
        reserveAvailable: Boolean,
        cursor: PerformanceId?,
        size: Int,
    ): CursorResult<Performance>
}
