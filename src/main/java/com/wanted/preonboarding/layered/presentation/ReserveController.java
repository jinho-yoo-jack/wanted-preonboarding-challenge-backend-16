package com.wanted.preonboarding.layered.presentation;

import com.wanted.preonboarding.layered.application.ReservationService;
import com.wanted.preonboarding.layered.application.cancel.CancelService;
import com.wanted.preonboarding.layered.application.ticketing.v3.TicketV3;
import com.wanted.preonboarding.layered.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.layered.domain.dto.request.RequestReservation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final ReservationService reservationService;
    private final CancelService cancelService;

    @PostMapping("/")
    public ResponseHandler<TicketV3> reservation() throws Exception {
        TicketV3 ticket = reservationService.reserveV3(RequestReservation.builder()
            .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
            .performanceName("레베카")
            .reservationName("유진호")
            .reservationPhoneNumber("010-1234-1234")
            .reservationStatus("reserve")
            .depositAmount(200000)
            .fixedPrice(200000)
            .round(1)
            .line('A')
            .seat(1)
            .appliedPolicies(Arrays.asList(new String[]{"telecome"}))
            .build());
        System.out.println("reservation");
        return ResponseHandler.<TicketV3>builder()
            .statusCode(HttpStatus.OK)
            .message("SUCCESS")
            .data(ticket)
            .build();
    }

    @GetMapping("/cancel")
    public String cancel(){
        cancelService.cancelTicket();
        return "SUCCESS";
    }
}
