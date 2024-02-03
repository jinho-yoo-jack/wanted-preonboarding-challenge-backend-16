package com.wanted.preonboarding.ticket.interfaces.controller;

import com.wanted.preonboarding.common.util.ResponseWrapper;
import com.wanted.preonboarding.ticket.application.ReservationService;
import com.wanted.preonboarding.ticket.interfaces.dto.CustomerContactRequest;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationCancelRequest;
import com.wanted.preonboarding.ticket.interfaces.dto.ReservationRequest;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReserveController {
    private final ReservationService reservationService;
    private final HttpServletRequest req;

    @PostMapping("")
    public ResponseEntity<?> reserve(@RequestBody ReservationRequest request) {
        System.out.println("reservation");

        return new ResponseEntity<>(new ResponseWrapper(req, HttpStatus.OK,
            true, "예매하였습니다.", reservationService.reserve(request)), HttpStatus.OK);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancel(@RequestBody ReservationCancelRequest request) {
        return new ResponseEntity<>(new ResponseWrapper(req, HttpStatus.OK,
            true, "예매 취소하였습니다.", reservationService.cancel(request)), HttpStatus.OK);
    }

    @PostMapping("/history")
    public ResponseEntity<?> findReserve(@RequestBody CustomerContactRequest request) {
        return new ResponseEntity<>(new ResponseWrapper(req, HttpStatus.OK,
            true, "예매 내역 조회.", reservationService.getReservations(request)), HttpStatus.OK);
    }
}
