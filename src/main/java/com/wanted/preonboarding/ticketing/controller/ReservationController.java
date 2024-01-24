package com.wanted.preonboarding.ticketing.controller;

import com.wanted.preonboarding.ticketing.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.request.ReadReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.response.CreateReservationResponse;
import com.wanted.preonboarding.ticketing.domain.dto.response.ReadPerformanceResponse;
import com.wanted.preonboarding.ticketing.domain.dto.response.ReadReservationResponse;
import com.wanted.preonboarding.ticketing.service.ReservationService;
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
    public ResponseEntity<CreateReservationResponse> create(@RequestBody CreateReservationRequest createReservationRequest) {
        return ResponseEntity.ok(reservationService.createReservation(createReservationRequest));
    }

    @GetMapping
    public ResponseEntity<Page<ReadReservationResponse>> readReservation(@ModelAttribute ReadReservationRequest reservationRequest,
                                                                         @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(reservationService.readReservation(reservationRequest, pageable));
    }

    @GetMapping("/performance")
    public ResponseEntity<Page<ReadPerformanceResponse>> readPerformance(@RequestParam String isReserve,
                                                                          @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(reservationService.readPerformance(isReserve, pageable));
    }
}
