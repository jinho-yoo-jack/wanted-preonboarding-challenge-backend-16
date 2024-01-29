package com.wanted.preonboarding.ticket.application.reservation.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.aop.annotation.ExecutionTimer;
import com.wanted.preonboarding.ticket.application.reservation.service.ReservationService;
import com.wanted.preonboarding.ticket.domain.dto.request.RequestReservation;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@ExecutionTimer
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/info/{code}")
    public ResponseEntity<ResponseHandler<ReservationResponse>> getReservationInfo(
            @PathVariable final String code
    ) {
        return reservationService.getReservationInfo(code);
    }

    @PostMapping("/proceed")
    public ResponseEntity<ResponseHandler<ReservationResponse>> proceedReservation(
            @RequestBody final RequestReservation requestReservation
    ) {
        return reservationService.proceedReservation(requestReservation);
    }

    @DeleteMapping("/cancel/{code}")
    public ResponseEntity<ResponseHandler<Void>> cancelReservation(
            @PathVariable final String code
    ) {
        return reservationService.cancelReservation(code);
    }


}
