package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceResponse;
import com.wanted.preonboarding.ticket.domain.dto.ReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.ReservationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final ReservationService reservationService;

    @PostMapping
    public ResponseEntity<ResponseHandler<ReservationResponse>> reservation(@RequestBody ReservationRequest request) {
        System.out.println("reservation");

        return ResponseEntity
            .ok()
            .body(ResponseHandler.<ReservationResponse>builder()
                .message("Success")
                .statusCode(HttpStatus.OK)
                .data(reservationService.reserve(request))
                .build()
            );
    }
}


//ReservationRequest.builder()
//            .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
//            .reservationName("유진호")
//            .reservationPhoneNumber("010-1234-1234")
//            .reservationStatus("reserve")
//            .amount(200000)
//            .round(1)
//            .line('A')
//            .seat(1)
//            .build()