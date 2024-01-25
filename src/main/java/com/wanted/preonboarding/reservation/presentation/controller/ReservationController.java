package com.wanted.preonboarding.reservation.presentation.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.reservation.application.service.ReservationService;
import com.wanted.preonboarding.reservation.domain.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/reserve")
@RequiredArgsConstructor
public class ReservationController {

    private final ReservationService reservationService;

    @PostMapping("/")
    public ResponseHandler<Object> reserve(@RequestBody ReservationRequest reservationRequest) {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.CREATED)
                .data(reservationService.reservePerformance(reservationRequest))
                .build();
    }

    @GetMapping("/")
    public ResponseHandler<Object> findReservations(@Param("name") String name, @Param("phone") String phoneNumber) {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.OK)
                .data(reservationService.findReservationsByUserInfo(UserInfo.of(name, phoneNumber)))
                .build();
    }
}
