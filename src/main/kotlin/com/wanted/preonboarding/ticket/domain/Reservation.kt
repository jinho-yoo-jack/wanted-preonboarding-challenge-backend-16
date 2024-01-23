package com.wanted.preonboarding.ticket.domain

import java.time.LocalDateTime

class Reservation(
    val id: ReservationId = ReservationId(0),
    private val userInfo: UserInfo,
    private val seatInfo: SeatInfo,
    private val createdAt: LocalDateTime = LocalDateTime.now(),
    private val updatedAt: LocalDateTime? = null,
) {
    fun isSameSeat(seatInfo: SeatInfo): Boolean {
        return this.seatInfo == seatInfo
    }

    fun getUserInfo(): UserInfo {
        return userInfo
    }

    fun getSeatInfo(): SeatInfo {
        return seatInfo
    }

    fun isReserved(userInfo: UserInfo): Boolean {
        return this.userInfo == userInfo
    }
}
