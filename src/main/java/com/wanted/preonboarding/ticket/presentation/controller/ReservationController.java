package com.wanted.preonboarding.ticket.presentation.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.aop.dto.BaseResDto;
import com.wanted.preonboarding.ticket.domain.dto.request.ReadReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.CreateReservationResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final TicketSeller ticketSeller;

    @PostMapping
    public ResponseEntity<ResponseHandler<CreateReservationResponse>> createReservation(@RequestBody CreateReservationRequest dto) {
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<CreateReservationResponse>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(ticketSeller.createReservation(dto))
                        .build());
    }


    @GetMapping("/customer")
    public ResponseEntity<ResponseHandler<CreateReservationResponse>> readReservation(@RequestBody ReadReservationRequest dto) {
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<CreateReservationResponse>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(ticketSeller.readReservation(dto))
                        .build());
    }
}

