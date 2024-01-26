package com.wanted.preonboarding.reservation.presentation.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.reservation.application.service.ReservationService;
import com.wanted.preonboarding.reservation.domain.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReservationController {

    private static final String DELETE_MESSAGE = "예약이 삭제되었습니다.";

    private final ReservationService reservationService;

    @PostMapping("/")
    public ResponseHandler<Object> reserve(@RequestBody ReservationRequest reservationRequest) {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.CREATED)
                .data(reservationService.reservePerformance(reservationRequest))
                .build();
    }

    @GetMapping("/")
    public ResponseHandler<Object> findReservations(@RequestParam String name, @RequestParam String phoneNumber) {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.OK)
                .data(reservationService.findReservationsByUserInfo(UserInfo.of(name, phoneNumber)))
                .build();
    }

    @DeleteMapping("/cancel")
    public ResponseHandler<Object> cancelReservation(@RequestParam int reservationId) {
        reservationService.cancelReservation(reservationId);
        return ResponseHandler.builder()
                .statusCode(HttpStatus.NO_CONTENT)
                .message(DELETE_MESSAGE)
                .build();
    }
}