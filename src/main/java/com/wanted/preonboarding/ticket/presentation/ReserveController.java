package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.TicketSeller;
import com.wanted.preonboarding.ticket.domain.dto.request.FindReserveRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.PerformanceListRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.ReserveInfoRequest;
import com.wanted.preonboarding.ticket.domain.dto.request.WaitReservationRequest;
import com.wanted.preonboarding.ticket.domain.dto.response.FindReserveResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceListResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.ReserveInfoResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.WaitReservationResponse;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
@Slf4j
public class ReserveController {

    private final TicketSeller ticketSeller;

    @PostMapping("/")
    public ResponseEntity<ReserveInfoResponse> reservation(@RequestBody ReserveInfoRequest request) {
        log.info("reserveInfo ID => {}", request.getPerformanceId());
        return ResponseEntity.ok(ticketSeller.reserve(request));
    }

    @GetMapping("/find")
    public ResponseEntity<FindReserveResponse> findReserveInfo(@RequestBody FindReserveRequest request) {
        return ResponseEntity.ok(ticketSeller.findReserveInfo(request));
    }

    @GetMapping("/available")
    public ResponseEntity<List<PerformanceListResponse>> findAvailablePerformance(@RequestBody PerformanceListRequest request) {
        return ResponseEntity.ok(ticketSeller.findAvailablePerformance(request));
    }

    @PostMapping("/wait")
    public ResponseEntity<WaitReservationResponse> waitReservation(WaitReservationRequest request) {
        return ResponseEntity.ok(ticketSeller.waitReservation(request));
    }
}
