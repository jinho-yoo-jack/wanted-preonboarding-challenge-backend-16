package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ApiResponse;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.application.dto.response.ReserveResponse;
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

        return ResponseEntity.ok(
                ApiResponse.ok(reservationService.reserve(request.toService()))
        );
    }
}
