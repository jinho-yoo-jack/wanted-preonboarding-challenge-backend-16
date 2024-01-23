package com.wanted.preonboarding.ticket.presentation

import com.jayway.jsonpath.JsonPath
import com.wanted.preonboarding.ticket.domain.PerformanceType
import com.wanted.preonboarding.ticket.domain.SeatInfoFixtureBuilder
import com.wanted.preonboarding.ticket.domain.UserInfoFixtureBuilder
import com.wanted.preonboarding.ticket.infra.entity.PerformanceEntity
import com.wanted.preonboarding.ticket.infra.entity.PerformanceSeatInfoEntity
import com.wanted.preonboarding.ticket.infra.entity.ReservationEntity
import com.wanted.preonboarding.ticket.presentation.request.ReservationCancelRequest
import com.wanted.preonboarding.ticket.presentation.request.ReservationRequest
import io.kotest.matchers.shouldBe
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.UUID

class ReservationControllerTest : IntegrationTest() {
    @Test
    fun `공연이 존재하지 않으면 예약에 실패한다`() {
        // given
        val request = ReservationRequest(
            performanceId = UUID.randomUUID(),
            userInfo = UserInfoFixtureBuilder().build(),
            balance = 10000,
            seatInfo = SeatInfoFixtureBuilder().build(),
        )

        // when
        val response = request(
            method = HttpMethod.POST,
            url = "/v1/reservations",
            body = request,
        )

        // then
        response.andExpect {
            val message = JsonPath.read(it.response.contentAsString, "$.error") as String
            message shouldBe "존재하지 않는 공연입니다."
        }
    }

    @Test
    fun `예약이 마감된 공연은 예약할 수 없다`() {
        // given
        val performance = save(
            PerformanceEntity(
                name = "performance",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = false,
            )
        )
        val request = ReservationRequest(
            performanceId = performance.id!!,
            userInfo = UserInfoFixtureBuilder().build(),
            balance = 10000,
            seatInfo = SeatInfoFixtureBuilder().build(),
        )

        // when
        val response = request(
            method = HttpMethod.POST,
            url = "/v1/reservations",
            body = request,
        )

        // then
        response.andExpect {
            val message = JsonPath.read(it.response.contentAsString, "$.error") as String
            message shouldBe "예약이 마감되었습니다."
        }
    }

    @Test
    fun `잔액이 부족하면 예약할 수 없다`() {
        // given
        val performance = save(
            PerformanceEntity(
                name = "performance",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            )
        )
        val request = ReservationRequest(
            performanceId = performance.id!!,
            userInfo = UserInfoFixtureBuilder().build(),
            balance = 10,
            seatInfo = SeatInfoFixtureBuilder().build(),
        )

        // when
        val response = request(
            method = HttpMethod.POST,
            url = "/v1/reservations",
            body = request,
        )

        // then
        response.andExpect {
            val message = JsonPath.read(it.response.contentAsString, "$.error") as String
            message shouldBe "잔액이 부족합니다."
        }
    }

    @Test
    fun `좌석이 존재하지 않으면 예약할 수 없다`() {
        // given
        val performance = save(
            PerformanceEntity(
                name = "performance",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            )
        )
        val request = ReservationRequest(
            performanceId = performance.id!!,
            userInfo = UserInfoFixtureBuilder().build(),
            balance = 10000,
            seatInfo = SeatInfoFixtureBuilder().build(),
        )

        // when
        val response = request(
            method = HttpMethod.POST,
            url = "/v1/reservations",
            body = request,
        )

        // then
        response.andExpect {
            val message = JsonPath.read(it.response.contentAsString, "$.error") as String
            message shouldBe "존재하지 않는 좌석입니다."
        }
    }

    @Test
    fun `예약할 수 없는 좌석은 예약할 수 없다`() {
        // given
        val performance = save(
            PerformanceEntity(
                name = "performance",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            )
        )
        val seatInfo = SeatInfoFixtureBuilder().build()
        save(
            PerformanceSeatInfoEntity(
                performanceId = performance.id!!,
                round = seatInfo.round,
                gate = seatInfo.gate,
                line = seatInfo.line,
                seat = seatInfo.seat,
                isReserve = false,
            )
        )
        val request = ReservationRequest(
            performanceId = performance.id!!,
            userInfo = UserInfoFixtureBuilder().build(),
            balance = 10000,
            seatInfo = seatInfo,
        )

        // when
        val response = request(
            method = HttpMethod.POST,
            url = "/v1/reservations",
            body = request,
        )

        // then
        response.andExpect {
            val message = JsonPath.read(it.response.contentAsString, "$.error") as String
            message shouldBe "존재하지 않는 좌석입니다."
        }
    }

    @Test
    fun `이미 예약된 좌석은 예약할 수 없다`() {
        // given
        val performance = save(
            PerformanceEntity(
                name = "performance",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            )
        )
        val seatInfo = SeatInfoFixtureBuilder().build()
        save(
            PerformanceSeatInfoEntity(
                performanceId = performance.id!!,
                round = seatInfo.round,
                gate = seatInfo.gate,
                line = seatInfo.line,
                seat = seatInfo.seat,
                isReserve = true,
            )
        )
        save(
            ReservationEntity(
                performanceId = performance.id!!,
                round = seatInfo.round,
                gate = seatInfo.gate,
                line = seatInfo.line,
                seat = seatInfo.seat,
                name = "name",
                phoneNumber = "01012345678",
            )
        )
        val request = ReservationRequest(
            performanceId = performance.id!!,
            userInfo = UserInfoFixtureBuilder().build(),
            balance = 10000,
            seatInfo = seatInfo,
        )

        // when
        val response = request(
            method = HttpMethod.POST,
            url = "/v1/reservations",
            body = request,
        )

        // then
        response.andExpect {
            val message = JsonPath.read(it.response.contentAsString, "$.error") as String
            message shouldBe "이미 예약된 좌석입니다."
        }
    }

    @Test
    fun `예약에 성공한다`() {
        // given
        val performance = save(
            PerformanceEntity(
                name = "performance",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            )
        )
        val seatInfo = SeatInfoFixtureBuilder().build()
        save(
            PerformanceSeatInfoEntity(
                performanceId = performance.id!!,
                round = seatInfo.round,
                gate = seatInfo.gate,
                line = seatInfo.line,
                seat = seatInfo.seat,
                isReserve = true,
            )
        )
        val request = ReservationRequest(
            performanceId = performance.id!!,
            userInfo = UserInfoFixtureBuilder().build(),
            balance = 10000,
            seatInfo = seatInfo,
        )

        // when
        val response = request(
            method = HttpMethod.POST,
            url = "/v1/reservations",
            body = request,
        )

        // then
        response.andExpect(status().isOk()).andExpect {
            val performanceId = JsonPath.read(it.response.contentAsString, "$.data.performanceId") as String
            val name = JsonPath.read(it.response.contentAsString, "$.data.userInfo.name") as String
            val phoneNumber = JsonPath.read(it.response.contentAsString, "$.data.userInfo.phoneNumber") as String
            val round = JsonPath.read(it.response.contentAsString, "$.data.seatInfo.round") as Int
            val gate = JsonPath.read(it.response.contentAsString, "$.data.seatInfo.gate") as Int
            val line = JsonPath.read(it.response.contentAsString, "$.data.seatInfo.line") as String
            val seat = JsonPath.read(it.response.contentAsString, "$.data.seatInfo.seat") as Int

            performanceId shouldBe performance.id.toString()
            name shouldBe request.userInfo.name
            phoneNumber shouldBe request.userInfo.phoneNumber
            round shouldBe seatInfo.round
            gate shouldBe seatInfo.gate
            line shouldBe seatInfo.line
            seat shouldBe seatInfo.seat

            val reservation = findOne<ReservationEntity>(
                """
                    SELECT r FROM ReservationEntity r
                    WHERE r.performanceId = :performanceId AND r.name = :name AND r.phoneNumber = :phoneNumber
                """.trimIndent(),
                mapOf(
                    "performanceId" to performance.id!!,
                    "name" to request.userInfo.name,
                    "phoneNumber" to request.userInfo.phoneNumber,
                )
            )
            reservation.performanceId shouldBe performance.id
            reservation.name shouldBe request.userInfo.name
            reservation.phoneNumber shouldBe request.userInfo.phoneNumber
            reservation.round shouldBe seatInfo.round
            reservation.gate shouldBe seatInfo.gate
            reservation.line shouldBe seatInfo.line
            reservation.seat shouldBe seatInfo.seat

            val performanceSeatInfo = findOne<PerformanceSeatInfoEntity>(
                """
                    SELECT psi FROM PerformanceSeatInfoEntity psi
                    WHERE psi.performanceId = :performanceId AND psi.round = :round AND psi.gate = :gate AND psi.line = :line AND psi.seat = :seat
                """.trimIndent(),
                mapOf(
                    "performanceId" to performance.id!!,
                    "round" to seatInfo.round,
                    "gate" to seatInfo.gate,
                    "line" to seatInfo.line,
                    "seat" to seatInfo.seat,
                )
            )
            performanceSeatInfo.isReserve shouldBe false
        }
    }

    @Test
    fun `공연이 존재하지 않으면 예약취소에 실패한다`() {
        // given
        val request = ReservationCancelRequest(
            performanceId = UUID.randomUUID(),
            userInfo = UserInfoFixtureBuilder().build(),
            seatInfo = SeatInfoFixtureBuilder().build(),
        )

        // when
        val response = request(
            method = HttpMethod.POST,
            url = "/v1/reservations/cancel",
            body = request,
        )

        // then
        response.andExpect {
            val message = JsonPath.read(it.response.contentAsString, "$.error") as String
            message shouldBe "존재하지 않는 공연입니다."
        }
    }

    @Test
    fun `좌석이 존재하지 않으면 예약을 취소할 수 없다`() {
        // given
        val performance = save(
            PerformanceEntity(
                name = "performance",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            )
        )
        val request = ReservationCancelRequest(
            performanceId = performance.id!!,
            userInfo = UserInfoFixtureBuilder().build(),
            seatInfo = SeatInfoFixtureBuilder().build(),
        )

        // when
        val response = request(
            method = HttpMethod.POST,
            url = "/v1/reservations/cancel",
            body = request,
        )

        // then
        response.andExpect {
            val message = JsonPath.read(it.response.contentAsString, "$.error") as String
            message shouldBe "존재하지 않는 좌석입니다."
        }
    }

    @Test
    fun `예약된 내역이 존재하지 않으면 예약을 취소할 수 없다`() {
        // given
        val performance = save(
            PerformanceEntity(
                name = "performance",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            )
        )
        val seatInfo = SeatInfoFixtureBuilder().build()
        save(
            PerformanceSeatInfoEntity(
                performanceId = performance.id!!,
                round = seatInfo.round,
                gate = seatInfo.gate,
                line = seatInfo.line,
                seat = seatInfo.seat,
                isReserve = true,
            )
        )
        val request = ReservationCancelRequest(
            performanceId = performance.id!!,
            userInfo = UserInfoFixtureBuilder().build(),
            seatInfo = seatInfo
        )

        // when
        val response = request(
            method = HttpMethod.POST,
            url = "/v1/reservations/cancel",
            body = request,
        )

        // then
        response.andExpect {
            val message = JsonPath.read(it.response.contentAsString, "$.error") as String
            message shouldBe "예약된 내역이 없습니다."
        }
    }

    @Test
    fun `타인이 한 예약은 취소할 수 없다`() {
        // given
        val performance = save(
            PerformanceEntity(
                name = "performance",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            )
        )
        val seatInfo = SeatInfoFixtureBuilder().build()
        save(
            PerformanceSeatInfoEntity(
                performanceId = performance.id!!,
                round = seatInfo.round,
                gate = seatInfo.gate,
                line = seatInfo.line,
                seat = seatInfo.seat,
                isReserve = true,
            )
        )
        save(
            ReservationEntity(
                performanceId = performance.id!!,
                round = seatInfo.round,
                gate = seatInfo.gate,
                line = seatInfo.line,
                seat = seatInfo.seat,
                name = "모르는 사람",
                phoneNumber = "01012345678",
            )
        )
        val request = ReservationCancelRequest(
            performanceId = performance.id!!,
            userInfo = UserInfoFixtureBuilder().build(),
            seatInfo = seatInfo,
        )

        // when
        val response = request(
            method = HttpMethod.POST,
            url = "/v1/reservations/cancel",
            body = request,
        )

        // then
        response.andExpect {
            val message = JsonPath.read(it.response.contentAsString, "$.error") as String
            message shouldBe "예약된 내역이 없습니다."
        }
    }

    @Test
    fun `예약 취소에 성공한다`() {
        // given
        val performance = save(
            PerformanceEntity(
                name = "performance",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            )
        )
        val seatInfo = SeatInfoFixtureBuilder().build()
        val userInfo = UserInfoFixtureBuilder().build()
        save(
            PerformanceSeatInfoEntity(
                performanceId = performance.id!!,
                round = seatInfo.round,
                gate = seatInfo.gate,
                line = seatInfo.line,
                seat = seatInfo.seat,
                isReserve = false,
            )
        )
        save(
            ReservationEntity(
                performanceId = performance.id!!,
                round = seatInfo.round,
                gate = seatInfo.gate,
                line = seatInfo.line,
                seat = seatInfo.seat,
                name = userInfo.name,
                phoneNumber = userInfo.phoneNumber
            )
        )
        val request = ReservationCancelRequest(
            performanceId = performance.id!!,
            userInfo = userInfo,
            seatInfo = seatInfo,
        )

        // when
        val response = request(
            method = HttpMethod.POST,
            url = "/v1/reservations/cancel",
            body = request,
        )

        // then
        response.andExpect(status().isOk()).andExpect {
            val reservation = findAll<ReservationEntity>(
                """
                    SELECT r FROM ReservationEntity r
                    WHERE r.performanceId = :performanceId AND r.name = :name AND r.phoneNumber = :phoneNumber
                """.trimIndent(),
                mapOf(
                    "performanceId" to performance.id!!,
                    "name" to userInfo.name,
                    "phoneNumber" to userInfo.phoneNumber,
                )
            )
            reservation.size shouldBe 0

            val performanceSeatInfo = findOne<PerformanceSeatInfoEntity>(
                """
                    SELECT psi FROM PerformanceSeatInfoEntity psi
                    WHERE psi.performanceId = :performanceId AND psi.round = :round AND psi.gate = :gate AND psi.line = :line AND psi.seat = :seat
                """.trimIndent(),
                mapOf(
                    "performanceId" to performance.id!!,
                    "round" to seatInfo.round,
                    "gate" to seatInfo.gate,
                    "line" to seatInfo.line,
                    "seat" to seatInfo.seat,
                )
            )
            performanceSeatInfo.isReserve shouldBe true
        }
    }
}
