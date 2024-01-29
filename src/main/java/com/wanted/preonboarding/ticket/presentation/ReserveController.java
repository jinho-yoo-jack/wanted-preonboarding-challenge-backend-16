package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.domain.dto.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.ReservationResponse;
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
    public ResponseEntity<ResponseHandler<ReservationResponse>> reservation(@RequestBody ReservationRequest request) {
        System.out.println("reservation");

        return ResponseEntity
            .ok()
            .body(ResponseHandler.<ReservationResponse>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(reservationService.reserve(request))
                .build()
            );
    }
}