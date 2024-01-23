package com.wanted.preonboarding.ticket.application.ticket

import com.wanted.preonboarding.core.CursorResult
import com.wanted.preonboarding.core.exception.ApplicationException
import com.wanted.preonboarding.ticket.domain.Performance
import com.wanted.preonboarding.ticket.domain.PerformanceId
import org.springframework.stereotype.Service

@Service
class PerformanceService(
    private val performancePort: PerformancePort,
) {
    fun findPerformance(id: PerformanceId): Performance {
        return performancePort.findPerformance(id)
            ?: throw ApplicationException.NotFoundException("존재하지 않는 공연입니다.")
    }

    fun findAllPerformance(
        reserveAvailable: Boolean,
        size: Int = 10,
        cursor: PerformanceId? = null,
    ): CursorResult<Performance> {
        return performancePort.findAllPerformanceByReserveAvailable(reserveAvailable, cursor, size)
    }
}
