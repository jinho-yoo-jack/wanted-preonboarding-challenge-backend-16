package com.wanted.preonboarding.ticket.application.ticket

import com.wanted.preonboarding.ticket.application.discount.DiscountPolicy
import com.wanted.preonboarding.ticket.domain.PerformanceFixtureBuilder
import com.wanted.preonboarding.ticket.domain.PerformanceId
import com.wanted.preonboarding.ticket.domain.PerformanceSeatInfoFixtureBuilder
import com.wanted.preonboarding.ticket.domain.ReservationFixtureBuilder
import com.wanted.preonboarding.ticket.domain.SeatInfoFixtureBuilder
import com.wanted.preonboarding.ticket.domain.UserInfoFixtureBuilder
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.mockk.every
import io.mockk.justRun
import io.mockk.mockk
import io.mockk.verify
import org.junit.jupiter.api.Test
import java.util.UUID

class ReservationServiceTest {
    @Test
    fun `공연이 존재하지 않으면, 예약에 실패한다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val sut = ReservationService(performancePort, emptyList())
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val balance = 1000
        val seatInfo = SeatInfoFixtureBuilder().build()
        every { performancePort.findPerformance(performanceId) } returns null

        // when
        val exception = shouldThrow<RuntimeException> {
            sut.reserve(performanceId, userInfo, balance, seatInfo)
        }

        // then
        exception.message shouldBe "존재하지 않는 공연입니다."
    }

    @Test
    fun `예약이 불가능한 공연은 예약에 실패한다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val sut = ReservationService(performancePort, emptyList())
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val balance = 1000
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance = PerformanceFixtureBuilder(
            id = performanceId,
            isReserve = false
        ).build()
        every { performancePort.findPerformance(performanceId) } returns performance

        // when
        val exception = shouldThrow<RuntimeException> {
            sut.reserve(performanceId, userInfo, balance, seatInfo)
        }

        // then
        exception.message shouldBe "예약이 마감되었습니다."
    }

    @Test
    fun `공연 금액보다 사용자의 결제 금액이 적으면, 예약에 실패한다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val sut = ReservationService(performancePort, emptyList())
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val balance = 1000
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance = PerformanceFixtureBuilder(
            id = performanceId,
            price = 10000
        ).build()
        every { performancePort.findPerformance(performanceId) } returns performance

        // when
        val exception = shouldThrow<RuntimeException> {
            sut.reserve(performanceId, userInfo, balance, seatInfo)
        }

        // then
        exception.message shouldBe "잔액이 부족합니다."
    }

    @Test
    fun `할인을 적용한 공연 금액보다 사용자의 결제 금액이 적으면, 예약에 실패한다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val eightyPercentDiscountPolicy = mockk<DiscountPolicy>()
        val sut = ReservationService(performancePort, listOf(eightyPercentDiscountPolicy))
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val balance = 1000
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance = PerformanceFixtureBuilder(
            id = performanceId,
            price = 10000
        ).build()
        every { performancePort.findPerformance(performanceId) } returns performance
        every { eightyPercentDiscountPolicy.isAcceptable(userInfo, seatInfo, performance) } returns true
        every { eightyPercentDiscountPolicy.discountRate() } returns 0.8

        // when
        val exception = shouldThrow<RuntimeException> {
            sut.reserve(performanceId, userInfo, balance, seatInfo)
        }

        // then
        exception.message shouldBe "잔액이 부족합니다."
    }

    @Test
    fun `예약하려는 좌석이 존재하지 않으면, 예약에 실패한다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val sut = ReservationService(performancePort, emptyList())
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val balance = 1000
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance = PerformanceFixtureBuilder(
            id = performanceId,
            price = 1000,
            performanceSeatInfos = mutableListOf(),
        ).build()
        every { performancePort.findPerformance(performanceId) } returns performance

        // when
        val exception = shouldThrow<RuntimeException> {
            sut.reserve(performanceId, userInfo, balance, seatInfo)
        }

        // then
        exception.message shouldBe "존재하지 않는 좌석입니다."
    }

    @Test
    fun `예약하려는 좌석이 존재하지만 예약을 받지 않는 상태라면, 예약에 실패한다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val sut = ReservationService(performancePort, emptyList())
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val balance = 1000
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance = PerformanceFixtureBuilder(
            id = performanceId,
            price = 1000,
            performanceSeatInfos = mutableListOf(
                PerformanceSeatInfoFixtureBuilder(
                    seatInfo = seatInfo,
                    isReserve = false
                ).build()
            ),
        ).build()
        every { performancePort.findPerformance(performanceId) } returns performance

        // when
        val exception = shouldThrow<RuntimeException> {
            sut.reserve(performanceId, userInfo, balance, seatInfo)
        }

        // then
        exception.message shouldBe "존재하지 않는 좌석입니다."
    }

    @Test
    fun `이미 예약된 좌석이라면, 예약에 실패한다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val sut = ReservationService(performancePort, emptyList())
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val balance = 1000
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance = PerformanceFixtureBuilder(
            id = performanceId,
            price = 1000,
            performanceSeatInfos = mutableListOf(
                PerformanceSeatInfoFixtureBuilder(
                    seatInfo = seatInfo
                ).build()
            ),
            reservations = mutableListOf(
                ReservationFixtureBuilder(
                    seatInfo = seatInfo,
                    userInfo = UserInfoFixtureBuilder().build()
                ).build()
            ),
        ).build()
        every { performancePort.findPerformance(performanceId) } returns performance

        // when
        val exception = shouldThrow<RuntimeException> {
            sut.reserve(performanceId, userInfo, balance, seatInfo)
        }

        // then
        exception.message shouldBe "이미 예약된 좌석입니다."
    }

    @Test
    fun `예약에 성공한다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val sut = ReservationService(performancePort, emptyList())
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val balance = 1000
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance = PerformanceFixtureBuilder(
            id = performanceId,
            price = 1000,
            performanceSeatInfos = mutableListOf(
                PerformanceSeatInfoFixtureBuilder(
                    seatInfo = seatInfo
                ).build()
            )
        ).build()
        every { performancePort.findPerformance(performanceId) } returns performance
        justRun { performancePort.update(performance) }

        // when
        sut.reserve(performanceId, userInfo, balance, seatInfo)

        // then
        performance.getPerformanceSeatInfos().find { it.isSameSeat(seatInfo) }?.isReserveAvailable() shouldBe false
        performance.getReservations().size shouldBe 1
        performance.getReservations().find { it.isReserved(userInfo) } shouldNotBe null
        performance.getReservations().find { it.isSameSeat(seatInfo) } shouldNotBe null
        verify(exactly = 1) { performancePort.update(performance) }
    }

    @Test
    fun `예약 가능한 상황에서 예매자가 가진 금액이 할인 후 공연 금액보다 크거나 같다면, 예약에 성공한다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val eightyPercentDiscountPolicy = mockk<DiscountPolicy>()
        val sut = ReservationService(performancePort, listOf(eightyPercentDiscountPolicy))
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val balance = 200
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance = PerformanceFixtureBuilder(
            id = performanceId,
            price = 1000,
            performanceSeatInfos = mutableListOf(
                PerformanceSeatInfoFixtureBuilder(
                    seatInfo = seatInfo
                ).build()
            )
        ).build()
        every { performancePort.findPerformance(performanceId) } returns performance
        every { eightyPercentDiscountPolicy.isAcceptable(userInfo, seatInfo, performance) } returns true
        every { eightyPercentDiscountPolicy.discountRate() } returns 0.8
        justRun { performancePort.update(performance) }

        // when
        sut.reserve(performanceId, userInfo, balance, seatInfo)

        // then
        performance.getPerformanceSeatInfos().find { it.isSameSeat(seatInfo) }?.isReserveAvailable() shouldBe false
        performance.getReservations().size shouldBe 1
        performance.getReservations().find { it.isReserved(userInfo) } shouldNotBe null
        performance.getReservations().find { it.isSameSeat(seatInfo) } shouldNotBe null
        verify(exactly = 1) { performancePort.update(performance) }
    }

    @Test
    fun `공연이 존재하지 않으면, 취소에 실패한다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val sut = ReservationService(performancePort, emptyList())
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val seatInfo = SeatInfoFixtureBuilder().build()
        every { performancePort.findPerformance(performanceId) } returns null

        // when
        val exception = shouldThrow<RuntimeException> {
            sut.cancel(performanceId, userInfo, seatInfo)
        }

        // then
        exception.message shouldBe "존재하지 않는 공연입니다."
    }

    @Test
    fun `존재하지 않는 좌석은 취소할 수 없다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val sut = ReservationService(performancePort, emptyList())
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance = PerformanceFixtureBuilder(
            id = performanceId
        ).build()
        every { performancePort.findPerformance(performanceId) } returns performance

        // when
        val exception = shouldThrow<RuntimeException> {
            sut.cancel(performanceId, userInfo, seatInfo)
        }

        // then
        exception.message shouldBe "존재하지 않는 좌석입니다."
    }

    @Test
    fun `존재하지 않는 예약은 취소할 수 없다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val sut = ReservationService(performancePort, emptyList())
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance = PerformanceFixtureBuilder(
            id = performanceId,
            performanceSeatInfos = mutableListOf(
                PerformanceSeatInfoFixtureBuilder(
                    seatInfo = seatInfo,
                    isReserve = false,
                ).build(),
            )
        ).build()
        every { performancePort.findPerformance(performanceId) } returns performance

        // when
        val exception = shouldThrow<RuntimeException> {
            sut.cancel(performanceId, userInfo, seatInfo)
        }

        // then
        exception.message shouldBe "예약된 내역이 없습니다."
    }

    @Test
    fun `자신이 예약하지 않은 예약은 취소할 수 없다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val sut = ReservationService(performancePort, emptyList())
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance = PerformanceFixtureBuilder(
            id = performanceId,
            performanceSeatInfos = mutableListOf(
                PerformanceSeatInfoFixtureBuilder(
                    seatInfo = seatInfo,
                    isReserve = false,
                ).build(),
            ),
            reservations = mutableListOf(
                ReservationFixtureBuilder(
                    seatInfo = seatInfo,
                    userInfo = UserInfoFixtureBuilder(
                        name = "김길동"
                    ).build(),
                ).build(),
            ),
        ).build()
        every { performancePort.findPerformance(performanceId) } returns performance

        // when
        val exception = shouldThrow<RuntimeException> {
            sut.cancel(performanceId, userInfo, seatInfo)
        }

        // then
        exception.message shouldBe "예약된 내역이 없습니다."
    }

    @Test
    fun `예약 취소에 성공한다`() {
        // given
        val performancePort = mockk<PerformancePort>()
        val sut = ReservationService(performancePort, emptyList())
        val performanceId = PerformanceId(UUID.randomUUID())
        val userInfo = UserInfoFixtureBuilder().build()
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance = PerformanceFixtureBuilder(
            id = performanceId,
            performanceSeatInfos = mutableListOf(
                PerformanceSeatInfoFixtureBuilder(
                    seatInfo = seatInfo,
                    isReserve = false,
                ).build(),
            ),
            reservations = mutableListOf(
                ReservationFixtureBuilder(
                    seatInfo = seatInfo,
                    userInfo = userInfo,
                ).build(),
            ),
        ).build()
        every { performancePort.findPerformance(performanceId) } returns performance
        justRun { performancePort.update(performance) }

        // when
        sut.cancel(performanceId, userInfo, seatInfo)

        // then
        verify { performancePort.update(performance) }
        performance.getPerformanceSeatInfos().find { it.isSameSeat(seatInfo) }?.isReserveAvailable() shouldBe true
        performance.getReservations().size shouldBe 0
    }
}
