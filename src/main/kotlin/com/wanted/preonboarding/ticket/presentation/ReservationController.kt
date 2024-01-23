package com.wanted.preonboarding.ticket.presentation

import com.wanted.preonboarding.ticket.application.ticket.ReservationService
import com.wanted.preonboarding.ticket.domain.PerformanceId
import com.wanted.preonboarding.ticket.presentation.common.ApiResponse
import com.wanted.preonboarding.ticket.presentation.request.ReservationCancelRequest
import com.wanted.preonboarding.ticket.presentation.request.ReservationRequest
import com.wanted.preonboarding.ticket.presentation.response.ReservationResponse
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class ReservationController(
    private val reservationService: ReservationService,
) {
    @PostMapping("/v1/reservations")
    fun reserve(
        @RequestBody request: ReservationRequest,
    ): ApiResponse<ReservationResponse> {
        val performance =
            reservationService.reserve(
                PerformanceId(request.performanceId),
                request.userInfo,
                request.balance,
                request.seatInfo,
            )

        return ApiResponse.success(ReservationResponse.from(performance, request.userInfo, request.seatInfo))
    }

    @PostMapping("/v1/reservations/cancel")
    fun cancel(
        @RequestBody request: ReservationCancelRequest,
    ): ApiResponse<Unit> {
        reservationService.cancel(
            PerformanceId(request.performanceId),
            request.userInfo,
            request.seatInfo,
        )
        return ApiResponse.success()
    }
}
