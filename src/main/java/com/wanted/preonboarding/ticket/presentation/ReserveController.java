package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.domain.dto.request.ReserveCreateRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReserveFindRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.ReserveCreateResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReserveFindResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final ReservationService reservationService;

    @PostMapping("/")
    public ResponseEntity<ResponseHandler<ReserveCreateResponse>> reservation(@RequestBody ReserveCreateRequest request) {
        System.out.println("reservation");
/*      //테스트용
        ReserveCreateResponse reserve = reservationService.reserve(ReserveCreateRequest.builder()
                .performanceId(UUID.fromString("4438a3e6-b01c-11ee-9426-0242ac180002"))
                .reservationName("JH")
                .reservationPhoneNumber("010-1234-5678")
                .balance(200000)
                .round(1)
                .line('A')
                .seat(1)
                .build());
*/
        ReserveCreateResponse reserve = reservationService.reserve(request);
        return ResponseEntity.ok()
                .body(ResponseHandler.<ReserveCreateResponse>builder()
                        .statusCode(HttpStatus.CREATED)
                        .message("Created")
                        .data(reserve)
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
