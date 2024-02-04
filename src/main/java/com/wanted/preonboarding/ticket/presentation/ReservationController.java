package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.ticket.TicketSellerService;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.ReservationCancelResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReserveInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/reservation")
@RequiredArgsConstructor
public class ReservationController {
    private final TicketSellerService ticketSellerService;
    @PostMapping
    public ResponseEntity<ResponseHandler<ReserveInfoResponse>> reservation(
            @RequestBody ReservationRequest request
    )
    {
        return ResponseEntity
                .ok()
                .body(
                        ResponseHandler.<ReserveInfoResponse>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(ticketSellerService.reserve(request))
                        .build()
                );
    }
    @GetMapping("/{reservationId}")
    public ResponseEntity<ResponseHandler<ReserveInfoResponse>> getReservationInfo(
            @PathVariable int reservationId
    )
    {
        return ResponseEntity
                .ok()
                .body(
                        ResponseHandler.<ReserveInfoResponse>builder()
                                .message("Success")
                                .statusCode(HttpStatus.OK)
                                .data(ticketSellerService.getReservationInfo(reservationId))
                                .build()
                );
    }
    @PutMapping
    public ResponseEntity<ResponseHandler<Void>> cancelReservation(
            @RequestBody ReservationCancelRequest request
    )
    {
        ticketSellerService.cancelReservation(request);
        return ResponseEntity
                .ok()
                .body(
                        ResponseHandler.<Void>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .build()
                );
    }

    @PostMapping("/cancel-alarms")
    public ResponseEntity<ResponseHandler<Void>> saveCancelAlarm(
            @RequestBody ReservationRequest request
    )
    {
        ticketSellerService.saveCancelAlarm(request);
        return ResponseEntity
                .ok()
                .body(
                        ResponseHandler.<Void>builder()
                                .message("Success")
                                .statusCode(HttpStatus.OK)
                                .build()
                );
    }
}