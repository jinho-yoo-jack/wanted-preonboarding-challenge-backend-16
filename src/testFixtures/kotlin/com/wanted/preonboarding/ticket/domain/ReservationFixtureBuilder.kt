package com.wanted.preonboarding.ticket.domain

import java.time.LocalDateTime

data class ReservationFixtureBuilder(
    val id: ReservationId = ReservationId(0),
    val userInfo: UserInfo = UserInfoFixtureBuilder().build(),
    val seatInfo: SeatInfo = SeatInfoFixtureBuilder().build(),
    val createdAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime? = null,
) {
    fun build(): Reservation {
        return Reservation(
            id = id,
            userInfo = userInfo,
            seatInfo = seatInfo,
            createdAt = createdAt,
            updatedAt = updatedAt,
        )
    }
}
