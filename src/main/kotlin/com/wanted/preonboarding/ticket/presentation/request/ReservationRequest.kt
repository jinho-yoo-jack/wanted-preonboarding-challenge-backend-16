package com.wanted.preonboarding.ticket.presentation.request

import com.wanted.preonboarding.ticket.domain.SeatInfo
import com.wanted.preonboarding.ticket.domain.UserInfo
import java.util.UUID

data class ReservationRequest(
    val performanceId: UUID,
    val seatInfo: SeatInfo,
    val userInfo: UserInfo,
    val balance: Int,
)
