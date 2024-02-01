package com.wanted.preonboarding.ticket.application.reservation.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.annotation.ExecutionTimer;
import com.wanted.preonboarding.ticket.application.reservation.service.ReservationService;
import com.wanted.preonboarding.ticket.domain.dto.request.RequestReservation;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.wanted.preonboarding.core.domain.response.ResponseHandler.MESSAGE_SUCCESS;
import static com.wanted.preonboarding.core.domain.response.ResponseHandler.createResponse;

@ExecutionTimer
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/detail/{code}")
    public ResponseEntity<ResponseHandler<ReservationResponse>> getReservationInfo(
            @PathVariable final String code
    ) {
        ReservationResponse response = reservationService.getReservationInfo(code);
        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, response);
    }

    @PostMapping("/proceed")
    public ResponseEntity<ResponseHandler<ReservationResponse>> proceedReservation(
            @RequestBody final RequestReservation requestReservation
    ) {
        ReservationResponse response = reservationService.proceedReservation(requestReservation);
        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, response);
    }

    @DeleteMapping("/cancel/{code}")
    public ResponseEntity<ResponseHandler<Void>> cancelReservation(
            @PathVariable final String code
    ) {
        reservationService.cancelReservation(code);
        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, null);
    }


}
