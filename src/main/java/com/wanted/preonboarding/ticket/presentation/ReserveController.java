package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.dto.request.ReserveFindRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.ReserveFindResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final TicketSeller ticketSeller;
    private final ReservationService reservationService;

    @PostMapping("/")
    public boolean reservation() {
        System.out.println("reservation");

        return ticketSeller.reserve(ReserveInfo.builder()
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .reservationName("유진호")
                .reservationPhoneNumber("010-1234-1234")
                .reservationStatus("reserve")
                .amount(200000)
                .round(1)
                .line('A')
                .seat(1)
                .build()
        );
    }

    @GetMapping("/")
    public ResponseEntity<ResponseHandler<List<ReserveFindResponse>>> findReservation(@RequestBody ReserveFindRequest request) {
        System.out.println("ReserveController.findReservation");    //TODO: 나중에 제거
        List<ReserveFindResponse> reservation = reservationService.findReservation(request);

        return ResponseEntity.ok()
                .body(ResponseHandler.<List<ReserveFindResponse>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(reservation)
                        .build());
    }
}
