package com.wanted.preonboarding.ticketing.controller;

import com.wanted.preonboarding.ticketing.domain.dto.request.CancelReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.request.ReadReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.response.CancelReservationResponse;
import com.wanted.preonboarding.ticketing.domain.dto.response.CreateReservationResponse;
import com.wanted.preonboarding.ticketing.domain.dto.response.ReadReservationResponse;
import com.wanted.preonboarding.ticketing.service.ReservationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<CreateReservationResponse> create(@RequestBody @Valid CreateReservationRequest createReservationRequest) {
        return ResponseEntity.ok(reservationService.createReservation(createReservationRequest));
    }

    @GetMapping
    public ResponseEntity<Page<ReadReservationResponse>> read(@ModelAttribute @Valid ReadReservationRequest reservationRequest,
                                                              @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(reservationService.readReservations(reservationRequest, pageable));
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<CancelReservationResponse> cancel(@RequestBody @Valid CancelReservationRequest cancelReservationRequest) {
        return ResponseEntity.ok(reservationService.cancelReservation(cancelReservationRequest));
    }
}
