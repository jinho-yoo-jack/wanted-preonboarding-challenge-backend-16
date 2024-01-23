package com.wanted.preonboarding.ticket.presentation.response

import com.wanted.preonboarding.ticket.domain.Performance
import com.wanted.preonboarding.ticket.domain.SeatInfo
import com.wanted.preonboarding.ticket.domain.UserInfo
import java.util.UUID

data class ReservationResponse(
    val performanceId: UUID,
    val name: String,
    val round: Int,
    val userInfo: UserInfo,
    val seatInfo: SeatInfo,
) {
    companion object {
        fun from(
            performance: Performance,
            userInfo: UserInfo,
            seatInfo: SeatInfo,
        ): ReservationResponse {
            return ReservationResponse(
                performanceId = performance.id.value,
                name = performance.name,
                round = performance.round,
                userInfo = userInfo,
                seatInfo = seatInfo,
            )
        }
    }
}
