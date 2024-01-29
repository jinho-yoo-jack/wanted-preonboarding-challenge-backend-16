package com.wanted.preonboarding.performanceSeat.presentation.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.performanceSeat.application.service.PerformanceSeatService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performanceSeat")
public class PerformanceSeatController {

    private final PerformanceSeatService performanceSeatService;

    @GetMapping("")
    public ResponseHandler<Object> findReservableSeatByPerformanceId(@RequestParam UUID performanceId) {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.OK)
                .data(performanceSeatService.findReservableSeats(performanceId))
                .build();
    }
}
