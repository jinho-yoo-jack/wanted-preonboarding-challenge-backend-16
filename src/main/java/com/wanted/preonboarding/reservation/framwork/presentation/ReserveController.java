package com.wanted.preonboarding.reservation.framwork.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.reservation.application.ReservationService;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.framwork.presentation.dto.ReservedItemResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ResponseHandler<ReservedItemResponse>> reservation(@RequestBody ReservationRequest request) {
        System.out.println("reservation");

        return ResponseEntity
            .ok()
            .body(ResponseHandler.<ReservedItemResponse>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(reservationService.reserve(request))
                .build()
            );
    }
}