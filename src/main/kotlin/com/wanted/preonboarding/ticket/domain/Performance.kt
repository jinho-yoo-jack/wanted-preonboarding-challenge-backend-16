package com.wanted.preonboarding.ticket.domain

import java.time.LocalDateTime
import java.util.UUID

class Performance(
    val id: PerformanceId = PerformanceId(UUID.randomUUID()),
    val name: String,
    val price: Int,
    val round: Int,
    val type: PerformanceType,
    val startDate: LocalDateTime,
    private val performanceSeatInfos: MutableList<PerformanceSeatInfo>,
    private val reservations: MutableList<Reservation>,
    private val isReserve: Boolean,
    private val createdAt: LocalDateTime,
    private val updatedAt: LocalDateTime? = null,
) {
    fun reserve(
        userInfo: UserInfo,
        balance: Int,
        seatInfo: SeatInfo,
        discountRate: Double = 0.0
    ) {
        if (!isReserve) throw RuntimeException("예약이 마감되었습니다.")
        if (balance < price * (1.0 - discountRate)) throw RuntimeException("잔액이 부족합니다.")
        if (performanceSeatInfos.find { it.isSameSeat(seatInfo) && it.isReserveAvailable() } == null) {
            throw RuntimeException("존재하지 않는 좌석입니다.")
        }
        if (reservations.find { it.isSameSeat(seatInfo) } != null) {
            throw RuntimeException("이미 예약된 좌석입니다.")
        }

        performanceSeatInfos.find { it.isSameSeat(seatInfo) }?.setReserveAvailable()
        reservations.add(
            Reservation(
                userInfo = userInfo,
                seatInfo = seatInfo,
            ),
        )
    }

    fun cancel(
        userInfo: UserInfo,
        seatInfo: SeatInfo,
    ) {
        if (performanceSeatInfos.find { it.isSameSeat(seatInfo) } == null) {
            throw RuntimeException("존재하지 않는 좌석입니다.")
        }
        val reservation =
            reservations.find { it.isSameSeat(seatInfo) && it.isReserved(userInfo) }
                ?: throw RuntimeException("예약된 내역이 없습니다.")

        performanceSeatInfos.find { it.isSameSeat(seatInfo) }?.setReserveNotAvailable()
        reservations.remove(reservation)
    }

    fun getReservations(): List<Reservation> {
        return reservations
    }

    fun getPerformanceSeatInfos(): List<PerformanceSeatInfo> {
        return performanceSeatInfos
    }
}
