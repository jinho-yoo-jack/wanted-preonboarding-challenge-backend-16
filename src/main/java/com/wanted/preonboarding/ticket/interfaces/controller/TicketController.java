package com.wanted.preonboarding.ticket.interfaces.controller;

import com.wanted.preonboarding.ticket.interfaces.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/ticket")
public class TicketController {

    private final ReservationService reservationService;


    @PostMapping(path = "/reservation")
    public ResultResponse<ReservationResponseDto> reserve(ReservationRequestDto reservationRequestDto) {
        return new ResultResponse<>(reservationService.reserve(reservationRequestDto));
    }

    @GetMapping(path = "/reservation/inquiry")
    public ResultResponse<ReservationInquiryDto> getReservationInquiry() {
        return new ResultResponse<>(null);
    }

    @GetMapping(path = "/performances")
    public ResultResponse<List<PerformanceDto>> getPerformances() {
        return new ResultResponse<>(null);
    }
    //todo 예약 가능 알림 서비스 - 취소건 발생시 예약 가능 알림 서비스 -> 알림을 뭐로 보낼지는 안 나와있구나, email로 보낼까. queue ?
}
