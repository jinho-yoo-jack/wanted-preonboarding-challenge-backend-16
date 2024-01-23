package com.wanted.preonboarding.ticket.application.discount

import com.wanted.preonboarding.ticket.domain.Performance
import com.wanted.preonboarding.ticket.domain.SeatInfo
import com.wanted.preonboarding.ticket.domain.UserInfo

interface DiscountPolicy {
    fun isAcceptable(
        userInfo: UserInfo,
        seatInfo: SeatInfo,
        performance: Performance,
    ): Boolean

    fun discountRate(): Double
}
