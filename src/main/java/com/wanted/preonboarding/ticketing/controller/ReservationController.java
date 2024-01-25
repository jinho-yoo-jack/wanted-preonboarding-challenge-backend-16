package com.wanted.preonboarding.ticketing.controller;

import com.wanted.preonboarding.ticketing.domain.dto.request.CancelReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.request.CreateAlarmRequest;
import com.wanted.preonboarding.ticketing.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.request.ReadReservationRequest;
import com.wanted.preonboarding.ticketing.domain.dto.response.*;
import com.wanted.preonboarding.ticketing.service.PerformanceService;
import com.wanted.preonboarding.ticketing.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/reservation")
public class ReservationController {
    private final ReservationService reservationService;
    private final PerformanceService performanceService;

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
        return ResponseEntity.ok(performanceService.readPerformance(isReserve, pageable));
    }

    @PostMapping("/alarm")
    public ResponseEntity<CreateAlarmResponse> createAlarm(@RequestBody CreateAlarmRequest createAlarmRequest) {
        return ResponseEntity.ok(reservationService.createAlarm(createAlarmRequest));
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<List<CancelReservationResponse>> cancel(@RequestBody CancelReservationRequest cancelReservationRequest) {
        return ResponseEntity.ok(reservationService.cancelReservation(cancelReservationRequest));
    }
}
