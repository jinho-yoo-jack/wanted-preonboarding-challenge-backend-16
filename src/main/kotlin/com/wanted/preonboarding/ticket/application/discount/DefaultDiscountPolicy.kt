package com.wanted.preonboarding.ticket.application.discount

import com.wanted.preonboarding.ticket.domain.Performance
import com.wanted.preonboarding.ticket.domain.SeatInfo
import com.wanted.preonboarding.ticket.domain.UserInfo
import org.springframework.stereotype.Component

@Component
class DefaultDiscountPolicy : DiscountPolicy {
    override fun isAcceptable(
        userInfo: UserInfo,
        seatInfo: SeatInfo,
        performance: Performance,
    ): Boolean {
        return true
    }

    override fun discountRate(): Double {
        return 0.01
    }
}
