package com.wanted.preonboarding.ticket.presentation

import com.wanted.preonboarding.core.CursorResult
import com.wanted.preonboarding.ticket.application.ticket.PerformanceService
import com.wanted.preonboarding.ticket.domain.PerformanceId
import com.wanted.preonboarding.ticket.presentation.common.ApiResponse
import com.wanted.preonboarding.ticket.presentation.response.PerformanceResponse
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.util.UUID

@RestController
class PerformanceController(
    private val performanceService: PerformanceService,
) {
    @GetMapping("/v1/performances/{performanceId}")
    fun getPerformance(
        @PathVariable performanceId: UUID,
    ): ApiResponse<PerformanceResponse> {
        val performance = performanceService.findPerformance(PerformanceId(performanceId))
        return ApiResponse.success(PerformanceResponse.from(performance))
    }

    @GetMapping("/v1/performances")
    fun findPerformances(
        @RequestParam reserveAvailable: Boolean,
        @RequestParam size: Int,
        @RequestParam(required = false) cursor: UUID?,
    ): ApiResponse<CursorResult<PerformanceResponse>> {
        val performances =
            performanceService.findAllPerformance(
                reserveAvailable,
                size,
                cursor?.let { PerformanceId(it) },
            )

        return ApiResponse.success(
            CursorResult(
                cursor = performances.cursor,
                hasNext = performances.hasNext,
                item = performances.item.map { PerformanceResponse.from(it) },
            ),
        )
    }
}
