package com.wanted.preonboarding.ticket.presentation.response

import com.wanted.preonboarding.ticket.domain.Performance
import java.time.LocalDateTime
import java.util.UUID

data class PerformanceResponse(
    val performanceId: UUID,
    val name: String,
    val round: Int,
    val startDate: LocalDateTime,
    val isReserve: Boolean,
) {
    companion object {
        fun from(performance: Performance): PerformanceResponse {
            return PerformanceResponse(
                performanceId = performance.id.value,
                name = performance.name,
                round = performance.round,
                startDate = performance.startDate,
                isReserve = performance.isReserveAvailable(),
            )
        }
    }
}
