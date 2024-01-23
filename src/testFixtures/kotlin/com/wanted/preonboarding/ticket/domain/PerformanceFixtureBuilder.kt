package com.wanted.preonboarding.ticket.domain

import java.time.LocalDateTime
import java.util.UUID

data class PerformanceFixtureBuilder(
    val id: PerformanceId = PerformanceId(UUID.randomUUID()),
    val name: String = "테스트 공연",
    val price: Int = 10000,
    val round: Int = 1,
    val type: PerformanceType = PerformanceType.NONE,
    val startDate: LocalDateTime = LocalDateTime.now(),
    val performanceSeatInfos: MutableList<PerformanceSeatInfo> = mutableListOf(),
    val reservations: MutableList<Reservation> = mutableListOf(),
    val isReserve: Boolean = true,
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null,
) {
    fun build(): Performance {
        return Performance(
            id = id,
            name = name,
            price = price,
            round = round,
            type = type,
            startDate = startDate,
            performanceSeatInfos = performanceSeatInfos,
            reservations = reservations,
            isReserve = isReserve,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
