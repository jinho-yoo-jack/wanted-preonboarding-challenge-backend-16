package com.wanted.preonboarding.ticket.presentation;

import static org.springframework.data.domain.Sort.Direction.DESC;

import com.wanted.preonboarding.ticket.application.reserve.ReserveService;
import com.wanted.preonboarding.ticket.dto.request.reservation.ReservationInfoRequest;
import com.wanted.preonboarding.ticket.dto.request.reservation.ReservationRequest;
import com.wanted.preonboarding.ticket.dto.response.page.PageResponse;
import com.wanted.preonboarding.ticket.dto.response.reservation.ReservationInfo;
import com.wanted.preonboarding.ticket.dto.result.ReservationModel;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

    @GetMapping("/reserve")
    public ResponseEntity<PageResponse<ReservationModel>> findReservation(@RequestBody ReservationInfoRequest request,
                                                                 @PageableDefault(sort = "createAt", direction = DESC) Pageable pageable) {
        PageResponse<ReservationModel> result = reserveService.findReservation(request.name(), request.phone(), pageable);
        return ResponseEntity.ok(result);
    }
}
