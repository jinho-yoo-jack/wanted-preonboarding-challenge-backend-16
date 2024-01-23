package com.wanted.preonboarding.ticket.domain

import java.time.LocalDateTime

class PerformanceSeatInfo(
    private val id: PerformanceSeatInfoId = PerformanceSeatInfoId(0),
    private val seatInfo: SeatInfo,
    private var isReserve: Boolean,
    private val createdAt: LocalDateTime,
    private val updatedAt: LocalDateTime? = null,
) {
    fun isSameSeat(seatInfo: SeatInfo): Boolean {
        return this.seatInfo == seatInfo
    }

    fun isReserveAvailable(): Boolean {
        return isReserve
    }

    fun setReserveAvailable() {
        isReserve = false
    }

    fun setReserveNotAvailable() {
        isReserve = true
    }
}
