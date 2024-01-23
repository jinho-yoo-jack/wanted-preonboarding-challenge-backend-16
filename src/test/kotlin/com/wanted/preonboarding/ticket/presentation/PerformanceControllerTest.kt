package com.wanted.preonboarding.ticket.presentation

import com.jayway.jsonpath.JsonPath
import com.wanted.preonboarding.ticket.domain.PerformanceType
import com.wanted.preonboarding.ticket.infra.entity.PerformanceEntity
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import org.junit.jupiter.api.Test
import org.springframework.http.HttpMethod
import org.springframework.test.web.servlet.result.MockMvcResultMatchers.status
import java.time.LocalDateTime
import java.util.UUID

class PerformanceControllerTest : IntegrationTest() {
    @Test
    fun `공연 조회에 성공한다`() {
        // given
        val entity =
            save(
                PerformanceEntity(
                    name = "performance",
                    type = PerformanceType.CONCERT,
                    price = 10000,
                    round = 1,
                    startDate = LocalDateTime.now(),
                    isReserve = true,
                ),
            )

        // when
        val response =
            request(
                method = HttpMethod.GET,
                url = "/v1/performances/${entity.id}",
            )

        // then
        response.andExpect(status().isOk).andExpect {
            val id = JsonPath.read(it.response.contentAsString, "$.data.performanceId") as String
            val name = JsonPath.read(it.response.contentAsString, "$.data.name") as String
            val round = JsonPath.read(it.response.contentAsString, "$.data.round") as Int
            val startDate = JsonPath.read(it.response.contentAsString, "$.data.startDate") as String
            val isReserve = JsonPath.read(it.response.contentAsString, "$.data.isReserve") as Boolean
            entity.id shouldBe UUID.fromString(id)
            entity.name shouldBe name
            entity.round shouldBe round
            entity.startDate shouldBe LocalDateTime.parse(startDate)
            entity.isReserve shouldBe isReserve
        }
    }

    @Test
    fun `공연 조회에 실패한다`() {
        // given
        val id = UUID.randomUUID()

        // when
        val response =
            request(
                method = HttpMethod.GET,
                url = "/v1/performances/$id",
            )

        // then
        response.andExpect(status().isNotFound).andExpect {
            val error = JsonPath.read(it.response.contentAsString, "$.error") as String
            error shouldBe "존재하지 않는 공연입니다."
        }
    }

    @Test
    fun `공연 다건 조회에 성공한다 - 커서가 없는 경우`() {
        // given
        save(
            PerformanceEntity(
                name = "performance",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            ),
        )
        save(
            PerformanceEntity(
                name = "performance2",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            ),
        )
        save(
            PerformanceEntity(
                name = "performance3",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = false,
            ),
        )

        // when
        val response =
            request(
                method = HttpMethod.GET,
                url = "/v1/performances?reserveAvailable=true&size=2",
            )

        // then
        response.andExpect(status().isOk).andExpect {
            val cursor = JsonPath.read(it.response.contentAsString, "$.data.cursor") as String
            val hasNext = JsonPath.read(it.response.contentAsString, "$.data.hasNext") as Boolean
            val item = JsonPath.read(it.response.contentAsString, "$.data.item") as List<Map<String, Any>>
            cursor shouldNotBe null
            hasNext shouldBe false
            item.size shouldBe 2
        }
    }

    @Test
    fun `공연 다건 조회에 성공한다 - 커서가 있는 경우`() {
        // given
        save(
            PerformanceEntity(
                name = "performance",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            ),
        )
        val cursorEntity =
            save(
                PerformanceEntity(
                    name = "performance2",
                    type = PerformanceType.CONCERT,
                    price = 10000,
                    round = 1,
                    startDate = LocalDateTime.now(),
                    isReserve = true,
                ),
            )
        save(
            PerformanceEntity(
                name = "performance3",
                type = PerformanceType.CONCERT,
                price = 10000,
                round = 1,
                startDate = LocalDateTime.now(),
                isReserve = true,
            ),
        )

        // when
        val response =
            request(
                method = HttpMethod.GET,
                url = "/v1/performances?reserveAvailable=true&size=2&cursor=${cursorEntity.id}",
            )

        // then
        response.andExpect(status().isOk).andExpect {
            val cursor = JsonPath.read(it.response.contentAsString, "$.data.cursor") as String
            val hasNext = JsonPath.read(it.response.contentAsString, "$.data.hasNext") as Boolean
            val item = JsonPath.read(it.response.contentAsString, "$.data.item") as List<Map<String, Any>>
            cursor shouldNotBe null
            hasNext shouldBe false
            item.size shouldBe 1
        }
    }

    @Test
    fun `커서 이후에 데이터가 없으면 다건 조회시 빈 데이터를 반환한다`() {
        // given
        val cursorEntity =
            save(
                PerformanceEntity(
                    name = "performance",
                    type = PerformanceType.CONCERT,
                    price = 10000,
                    round = 1,
                    startDate = LocalDateTime.now(),
                    isReserve = true,
                ),
            )

        // when
        val response =
            request(
                method = HttpMethod.GET,
                url = "/v1/performances?reserveAvailable=true&size=2&cursor=${cursorEntity.id}",
            )

        // then
        response.andExpect(status().isOk).andExpect {
            val cursor = JsonPath.read(it.response.contentAsString, "$.data.cursor") as String?
            val hasNext = JsonPath.read(it.response.contentAsString, "$.data.hasNext") as Boolean
            val item = JsonPath.read(it.response.contentAsString, "$.data.item") as List<Map<String, Any>>
            cursor shouldBe null
            hasNext shouldBe false
            item.size shouldBe 0
        }
    }
}
