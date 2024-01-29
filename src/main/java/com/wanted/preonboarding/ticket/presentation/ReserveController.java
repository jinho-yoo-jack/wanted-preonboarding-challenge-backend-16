package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.reserve.ReserveService;
import com.wanted.preonboarding.ticket.dto.request.reservation.ReservationRequest;
import com.wanted.preonboarding.ticket.dto.response.reservation.ReservationInfo;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RequiredArgsConstructor
@RequestMapping("/v1/app")
@RestController
public class ReserveController {

    private final ReserveService reserveService;

    @PostMapping("/reserve")
    public ResponseEntity<ReservationInfo> reservePerformance(@RequestBody ReservationRequest request) {
        LocalDateTime requestTime = LocalDateTime.now();
        ReservationInfo reservationInfo = reserveService.reserve(request, requestTime);
        return ResponseEntity.ok(reservationInfo);
    }
}
