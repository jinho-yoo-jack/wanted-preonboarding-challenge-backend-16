package com.wanted.preonboarding.ticket.domain

import io.kotest.assertions.throwables.shouldThrow
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test

class ReservationTest {
    @Test
    fun `현재 예약을 받고있지 않은 공연은 예약에 실패한다`() {
        // given
        val performance =
            PerformanceFixtureBuilder(
                isReserve = false,
            ).build()

        // when
        val exception =
            shouldThrow<RuntimeException> {
                performance.reserve(
                    userInfo = UserInfoFixtureBuilder().build(),
                    balance = 10000,
                    seatInfo = SeatInfoFixtureBuilder().build(),
                )
            }

        // then
        exception.message shouldBe "예약이 마감되었습니다."
    }

    @Test
    fun `공연 금액보다 사용자의 결제 금액이 적으면, 예약에 실패한다`() {
        // given
        val performance =
            PerformanceFixtureBuilder(
                price = 10000,
            ).build()

        // when
        val exception =
            shouldThrow<RuntimeException> {
                performance.reserve(
                    userInfo = UserInfoFixtureBuilder().build(),
                    balance = 9999,
                    seatInfo = SeatInfoFixtureBuilder().build(),
                )
            }

        // then
        exception.message shouldBe "잔액이 부족합니다."
    }

    @Test
    fun `할인율을 적용한 공연 금액보다 사용자의 결제 금액이 적으면, 예약에 실패한다`() {
        // given
        val performance =
            PerformanceFixtureBuilder(
                price = 10000,
            ).build()

        // when
        val exception =
            shouldThrow<RuntimeException> {
                performance.reserve(
                    userInfo = UserInfoFixtureBuilder().build(),
                    balance = 9000,
                    seatInfo = SeatInfoFixtureBuilder().build(),
                    discountRate = 0.001,
                )
            }

        // then
        exception.message shouldBe "잔액이 부족합니다."
    }

    @Test
    fun `예약하려는 좌석이 존재하지 않으면, 예약에 실패한다`() {
        // given
        val performance =
            PerformanceFixtureBuilder(
                price = 1000,
                performanceSeatInfos = mutableListOf(),
            ).build()

        // when
        val exception =
            shouldThrow<RuntimeException> {
                performance.reserve(
                    userInfo = UserInfoFixtureBuilder().build(),
                    balance = 9999,
                    seatInfo = SeatInfoFixtureBuilder().build(),
                )
            }

        // then
        exception.message shouldBe "존재하지 않는 좌석입니다."
    }

    @Test
    fun `예약하려는 좌석이 존재하지만 예약을 받지 않는 상태라면, 예약에 실패한다`() {
        // given
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance =
            PerformanceFixtureBuilder(
                price = 1000,
                performanceSeatInfos =
                    mutableListOf(
                        PerformanceSeatInfoFixtureBuilder(
                            seatInfo = seatInfo,
                            isReserve = false,
                        ).build(),
                    ),
            ).build()

        // when
        val exception =
            shouldThrow<RuntimeException> {
                performance.reserve(
                    userInfo = UserInfoFixtureBuilder().build(),
                    balance = 9999,
                    seatInfo = seatInfo,
                )
            }

        // then
        exception.message shouldBe "존재하지 않는 좌석입니다."
    }

    @Test
    fun `이미 예약된 좌석이라면, 예약에 실패한다`() {
        // given
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance =
            PerformanceFixtureBuilder(
                price = 1000,
                performanceSeatInfos =
                    mutableListOf(
                        PerformanceSeatInfoFixtureBuilder(
                            seatInfo = seatInfo,
                            isReserve = true,
                        ).build(),
                    ),
                reservations =
                    mutableListOf(
                        ReservationFixtureBuilder(
                            seatInfo = seatInfo,
                        ).build(),
                    ),
            ).build()

        // when
        val exception =
            shouldThrow<RuntimeException> {
                performance.reserve(
                    userInfo = UserInfoFixtureBuilder().build(),
                    balance = 9999,
                    seatInfo = seatInfo,
                )
            }

        // then
        exception.message shouldBe "이미 예약된 좌석입니다."
    }

    @Test
    fun `예약에 성공한다`() {
        // given
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance =
            PerformanceFixtureBuilder(
                price = 1000,
                performanceSeatInfos =
                    mutableListOf(
                        PerformanceSeatInfoFixtureBuilder(
                            seatInfo = seatInfo,
                            isReserve = true,
                        ).build(),
                    ),
            ).build()
        val userInfo = UserInfoFixtureBuilder().build()

        // when
        performance.reserve(
            userInfo = userInfo,
            balance = 9999,
            seatInfo = seatInfo,
        )

        // then
        performance.getPerformanceSeatInfos().find { it.isSameSeat(seatInfo) }?.isReserveAvailable() shouldBe false
        performance.getReservations().size shouldBe 1
        performance.getReservations().find { it.isReserved(userInfo) } shouldNotBe null
        performance.getReservations().find { it.isSameSeat(seatInfo) } shouldNotBe null
    }

    @Test
    fun `예약 가능한 상황에서 예매자가 가진 금액이 할인 후 공연 금액보다 크거나 같다면, 예약에 성공한다`() {
        // given
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance =
            PerformanceFixtureBuilder(
                price = 1000,
                performanceSeatInfos =
                    mutableListOf(
                        PerformanceSeatInfoFixtureBuilder(
                            seatInfo = seatInfo,
                            isReserve = true,
                        ).build(),
                    ),
            ).build()
        val userInfo = UserInfoFixtureBuilder().build()

        // when
        performance.reserve(
            userInfo = userInfo,
            balance = 999,
            seatInfo = seatInfo,
            discountRate = 0.001,
        )

        // then
        performance.getPerformanceSeatInfos().find { it.isSameSeat(seatInfo) }?.isReserveAvailable() shouldBe false
        performance.getReservations().size shouldBe 1
        performance.getReservations().find { it.isReserved(userInfo) } shouldNotBe null
        performance.getReservations().find { it.isSameSeat(seatInfo) } shouldNotBe null
    }

    @Test
    fun `존재하지 않는 좌석은 취소할 수 없다`() {
        // given
        val performance =
            PerformanceFixtureBuilder(
                price = 1000,
            ).build()

        // when
        val exception =
            shouldThrow<RuntimeException> {
                performance.cancel(
                    userInfo = UserInfoFixtureBuilder().build(),
                    seatInfo = SeatInfoFixtureBuilder().build(),
                )
            }

        // then
        exception.message shouldBe "존재하지 않는 좌석입니다."
    }

    @Test
    fun `존재하지 않는 예약은 취소할 수 없다`() {
        // given
        val seatInfo = SeatInfoFixtureBuilder().build()
        val performance =
            PerformanceFixtureBuilder(
                price = 1000,
                performanceSeatInfos =
                    mutableListOf(
                        PerformanceSeatInfoFixtureBuilder(
                            seatInfo = seatInfo,
                            isReserve = true,
                        ).build(),
                    ),
            ).build()

        // when
        val exception =
            shouldThrow<RuntimeException> {
                performance.cancel(
                    userInfo = UserInfoFixtureBuilder().build(),
                    seatInfo = seatInfo,
                )
            }

        // then
        exception.message shouldBe "예약된 내역이 없습니다."
    }

    @Test
    fun `자신이 예약하지 않은 예약은 취소할 수 없다`() {
        // given
        val seatInfo = SeatInfoFixtureBuilder().build()
        val userInfo = UserInfoFixtureBuilder().build()
        val performance =
            PerformanceFixtureBuilder(
                price = 1000,
                performanceSeatInfos =
                    mutableListOf(
                        PerformanceSeatInfoFixtureBuilder(
                            seatInfo = seatInfo,
                            isReserve = false,
                        ).build(),
                    ),
                reservations =
                    mutableListOf(
                        ReservationFixtureBuilder(
                            seatInfo = seatInfo,
                            userInfo =
                                UserInfoFixtureBuilder(
                                    name = "김길동",
                                ).build(),
                        ).build(),
                    ),
            ).build()

        // when
        val exception =
            shouldThrow<RuntimeException> {
                performance.cancel(
                    userInfo = userInfo,
                    seatInfo = seatInfo,
                )
            }

        // then
        exception.message shouldBe "예약된 내역이 없습니다."
    }

    @Test
    fun `예약 취소에 성공한다`() {
        // given
        val seatInfo = SeatInfoFixtureBuilder().build()
        val userInfo = UserInfoFixtureBuilder().build()
        val performance =
            PerformanceFixtureBuilder(
                price = 1000,
                performanceSeatInfos =
                    mutableListOf(
                        PerformanceSeatInfoFixtureBuilder(
                            seatInfo = seatInfo,
                            isReserve = false,
                        ).build(),
                    ),
                reservations =
                    mutableListOf(
                        ReservationFixtureBuilder(
                            seatInfo = seatInfo,
                            userInfo = userInfo,
                        ).build(),
                    ),
            ).build()

        // when
        performance.cancel(
            userInfo = userInfo,
            seatInfo = seatInfo,
        )

        // then
        performance.getPerformanceSeatInfos().find { it.isSameSeat(seatInfo) }?.isReserveAvailable() shouldBe true
        performance.getReservations().size shouldBe 0
    }
}
