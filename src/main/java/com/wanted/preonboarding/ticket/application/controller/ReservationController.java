package com.wanted.preonboarding.ticket.application.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.domain.dto.RequestReservation;
import com.wanted.preonboarding.ticket.domain.dto.ReservationResponse;
import com.wanted.preonboarding.ticket.application.service.ReservationService;
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
            final @RequestParam("name") String name,
            final @RequestParam("phone_number") String phoneNumber
    ) {
        return reservationService.getReservationInfo(name, phoneNumber);
    }

    @PostMapping("/process")
    public ResponseEntity<ResponseHandler<ReservationResponse>> processReservation(
            final @RequestBody RequestReservation requestReservation
    ) {
        return reservationService.processReservation(requestReservation);
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<ResponseHandler<Void>> cancelReservation(
            final @PathVariable int id
    ) {
        return reservationService.cancelReservation(id);
    }


}
