package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationCreateRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationFindRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationCancelResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationCreateResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationFindResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
@Slf4j
public class ReservationController {
    private final ReservationService reservationService;

    @PostMapping("/")
    public ResponseEntity<ResponseHandler<ReservationCreateResponse>> reserve(@RequestBody ReservationCreateRequest request) {
        log.info("ReserveController.reserve");
        ReservationCreateResponse reserve = reservationService.reserve(request);
        return ResponseEntity.ok()
                .body(ResponseHandler.<ReservationCreateResponse>builder()
                        .statusCode(HttpStatus.CREATED)
                        .message("Created")
                        .data(reserve)
                        .build()
                );
    }

    @GetMapping("/")
    public ResponseEntity<ResponseHandler<List<ReservationFindResponse>>> findReservation(@ModelAttribute ReservationFindRequest request) {
        log.info("ReserveController.findReservation");
        List<ReservationFindResponse> reservation = reservationService.findReservation(request);

        return ResponseEntity.ok()
                .body(ResponseHandler.<List<ReservationFindResponse>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(reservation)
                        .build());
    }

    @DeleteMapping("/cancel")
    public ResponseEntity<ResponseHandler<ReservationCancelResponse>> deleteReservation(@RequestBody ReservationCancelRequest request) {
        log.info("ReservationController.deleteReservation");
        ReservationCancelResponse reservationCancelResponse = reservationService.cancelReservation(request);
        return ResponseEntity.ok()
                .body(ResponseHandler.<ReservationCancelResponse>builder()
                        .message("success")
                        .statusCode(HttpStatus.OK)
                        .data(reservationCancelResponse)
                        .build()
                );
    }
}
