package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ApiResponse;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.application.dto.response.ReserveResponse;
import com.wanted.preonboarding.ticket.presentation.dto.request.CancelReservationRequest;
import com.wanted.preonboarding.ticket.presentation.dto.request.CreateReserveInfoRequest;
import com.wanted.preonboarding.ticket.presentation.dto.request.FindReserveInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/reserves")
@RequiredArgsConstructor
public class ReserveController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<ReserveResponse>>> findReserve(
            @ModelAttribute @Validated FindReserveInfoRequest request
    ) {
        return ResponseEntity.ok(
                ApiResponse.ok(reservationService.findReserve(request.toService()))
        );
    }

    @PostMapping
    public ResponseEntity<ApiResponse<ReserveResponse>> reserve(
            @RequestBody @Validated CreateReserveInfoRequest request
    ) {
        // 단일 예약 조회 API가 존재한다면 location을 전달해주어야 하고 응답 코드는 201 이여야 함. /api/v1/reserves/{id}
        return ResponseEntity.ok(
                ApiResponse.ok(reservationService.reserve(request.toService()))
        );
    }

    @PostMapping("/{reservationId}/cancel")
    public ResponseEntity<ApiResponse<Void>> cancel(@PathVariable Long reservationId,
                                                    @RequestBody CancelReservationRequest request
    ) {
        reservationService.cancel(reservationId, request.userId());

        return ResponseEntity.ok(
                ApiResponse.ok(null)
        );
    }
}
