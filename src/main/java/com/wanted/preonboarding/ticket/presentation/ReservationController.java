package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.presentation.dto.ReservationCreateRequest;
import com.wanted.preonboarding.ticket.presentation.dto.ReservationRequest;
import com.wanted.preonboarding.ticket.presentation.dto.ReservationResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/reservations")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @GetMapping
    public ResponseEntity<ResponseHandler<List<ReservationResponse>>> getReservations(ReservationRequest request){
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<List<ReservationResponse>>builder()
                        .statusCode(HttpStatus.OK)
                        .message("Success")
                        .data(reservationService.getReservations(request.getName(), request.getPhoneNumber()).stream()
                                .map(ReservationResponse::of)
                                .collect(Collectors.toList()))
                        .build()
                );
    }

    @PostMapping
    public ResponseEntity<ResponseHandler<ReservationResponse>> reserve(@RequestBody ReservationCreateRequest request) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ResponseHandler.<ReservationResponse>builder()
                        .statusCode(HttpStatus.CREATED)
                        .message("Success")
                        .data(ReservationResponse.of(reservationService.reserve(request.toDto())))
                        .build()
                );
    }
}
