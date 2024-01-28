package com.wanted.preonboarding.ticket.application.reservation.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.reservation.service.ReservationService;
import com.wanted.preonboarding.ticket.domain.dto.request.RequestReservation;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/reservation")
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping("/info")
    public ResponseEntity<ResponseHandler<ReservationResponse>> getReservationInfo(
            @RequestParam("name") final String name,
            @RequestParam("phone_number") final String phoneNumber
    ) {
        return reservationService.getReservationInfo(name, phoneNumber);
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
