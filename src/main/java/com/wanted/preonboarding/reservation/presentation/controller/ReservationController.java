package com.wanted.preonboarding.reservation.presentation.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.reservation.application.service.ReservationService;
import com.wanted.preonboarding.reservation.domain.dto.ReservationRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/")
    public ResponseHandler<Object> reserve(@RequestBody ReservationRequest reservationRequest) {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.CREATED)
                .data(reservationService.reservePerformance(reservationRequest))
                .build();
    }
}
