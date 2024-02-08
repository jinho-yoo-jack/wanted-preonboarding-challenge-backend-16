package com.wanted.preonboarding.ticket.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.controller.model.ReservationApplyModel;
import com.wanted.preonboarding.ticket.controller.model.request.ReservationApplyRequest;
import com.wanted.preonboarding.ticket.service.ReservationService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/reservations")
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ResponseHandler<ReservationApplyModel>> apply(@RequestBody ReservationApplyRequest request) {
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<ReservationApplyModel>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(reservationService.apply(request))
                        .build()
                );
    }

    @GetMapping
    public ResponseEntity<ResponseHandler<List<ReservationApplyModel>>> getReservations(@RequestParam String userName,
                                                                                        @RequestParam String phoneNumber) {
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<List<ReservationApplyModel>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(reservationService.getReservations(userName, phoneNumber))
                        .build()
                );
    }

    @PatchMapping("/{id}/cancel")
    public ResponseEntity<ResponseHandler<Long>> cancel(@PathVariable Long id,
                                                           @RequestParam String userName,
                                                           @RequestParam String phoneNumber) {
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<Long>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(reservationService.cancel(id, userName, phoneNumber))
                        .build()
                );
    }
}
