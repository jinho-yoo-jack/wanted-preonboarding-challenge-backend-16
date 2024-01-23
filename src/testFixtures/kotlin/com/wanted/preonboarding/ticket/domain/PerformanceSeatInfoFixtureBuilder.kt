package com.wanted.preonboarding.ticket.domain

import java.time.LocalDateTime

data class PerformanceSeatInfoFixtureBuilder(
    val id: PerformanceSeatInfoId = PerformanceSeatInfoId(0),
    val seatInfo: SeatInfo = SeatInfoFixtureBuilder().build(),
    val isReserve: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null,
) {
    fun build(): PerformanceSeatInfo {
        return PerformanceSeatInfo(
            id = id,
            seatInfo = seatInfo,
            isReserve = isReserve,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
