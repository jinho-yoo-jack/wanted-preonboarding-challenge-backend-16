package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ApiResponse;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.application.dto.response.ReserveResponse;
import com.wanted.preonboarding.ticket.presentation.dto.request.CreateReserveInfoRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/reserves")
@RequiredArgsConstructor
public class ReserveController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ApiResponse<ReserveResponse>> reserve(
            @RequestBody @Validated CreateReserveInfoRequest request
    ) {

        return ResponseEntity.ok(
                ApiResponse.ok(reservationService.reserve(request.toService()))
        );
    }
}
