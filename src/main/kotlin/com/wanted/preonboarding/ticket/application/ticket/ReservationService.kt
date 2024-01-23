package com.wanted.preonboarding.ticket.application.ticket

import com.wanted.preonboarding.ticket.application.discount.DiscountPolicy
import com.wanted.preonboarding.ticket.domain.Performance
import com.wanted.preonboarding.ticket.domain.PerformanceId
import com.wanted.preonboarding.ticket.domain.SeatInfo
import com.wanted.preonboarding.ticket.domain.UserInfo
import jakarta.transaction.Transactional
import org.springframework.stereotype.Service

@Service
class ReservationService(
    private val performancePort: PerformancePort,
    private val discountPolicies: List<DiscountPolicy>,
) {
    @Transactional
    fun reserve(
        performanceId: PerformanceId,
        userInfo: UserInfo,
        balance: Int,
        seatInfo: SeatInfo,
    ): Performance {
        val performance =
            performancePort.findPerformance(performanceId)
                ?: throw RuntimeException("존재하지 않는 공연입니다.")

        val maxDiscountRate =
            discountPolicies.filter {
                it.isAcceptable(userInfo, seatInfo, performance)
            }.maxOfOrNull {
                it.discountRate()
            } ?: 0.0

        performance.reserve(userInfo, balance, seatInfo, maxDiscountRate)
        performancePort.update(performance)
        return performance
    }

    @Transactional
    fun cancel(
        performanceId: PerformanceId,
        userInfo: UserInfo,
        seatInfo: SeatInfo,
    ): Performance {
        val performance =
            performancePort.findPerformance(performanceId)
                ?: throw RuntimeException("존재하지 않는 공연입니다.")

        performance.cancel(userInfo, seatInfo)
        performancePort.update(performance)
        return performance
    }
}
