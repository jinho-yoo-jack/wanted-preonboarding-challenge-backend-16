package com.wanted.preonboarding.ticket.interfaces.controller;

import com.wanted.preonboarding.ticket.application.service.ReservationService;
import com.wanted.preonboarding.ticket.interfaces.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final ReservationService reservationService;

    @PostMapping(path = "/reservation")
    public ResultResponse<ReservationResponseDto> reserve(@RequestBody ReservationRequestDto reservationRequestDto) {
        return new ResultResponse<>(reservationService.reserve(reservationRequestDto));
    }

    @GetMapping(path = "/reservation/inquiry")
    public ResultResponse<ReservationInquiryDto> getReservationInquiry(@RequestBody ReservationInquiryRequestDto dto) {
        return new ResultResponse<>(reservationService.getReservationInquiry(dto));
    }

    @GetMapping(path = "/performances")
    public ResultResponse<List<PerformanceDto>> getPerformances() {
        return new ResultResponse<>(reservationService.getPerformances());
    }

    @GetMapping(path = "/performance/{id}")
    public ResultResponse<PerformanceDto> getPerformanceDetail(@PathVariable(name = "id") UUID id) {
        return new ResultResponse<>(reservationService.getPerformanceDetail(id));
    }

    @PostMapping(path = "/notification")
    public ResultResponse<EmptyResultResponse> notice() {
        return new ResultResponse<>(null);
    }
}
