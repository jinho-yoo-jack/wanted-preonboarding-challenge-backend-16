package com.wanted.preonboarding.performance.presentation.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.performance.application.service.PerformanceService;
import com.wanted.preonboarding.performance.domain.dto.CreateDiscountRequest;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @PostMapping("/await")
    public ResponseHandler<Object> awaitPerformanceReservation(@RequestBody UserInfo userInfo,
                                                               @RequestParam  UUID performanceId) {
        performanceService.addWaitingReservation(userInfo, performanceId);
        return ResponseHandler.builder()
                .statusCode(HttpStatus.CREATED)
                .build();
    }

    @GetMapping("")
    public ResponseHandler<Object> findAllPerformanceByReserveState(@RequestParam String isReserve) {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.OK)
                .data(performanceService.findPerformancesInReserveState(isReserve))
                .build();
    }

    @PostMapping("/discount")
    public ResponseHandler<Object> addDiscount(@RequestParam String adminKey, @RequestBody CreateDiscountRequest request) {
        performanceService.addDiscount(new CreateDiscountRequest(), adminKey);
        return ResponseHandler.builder()
                .statusCode(HttpStatus.CREATED)
                .message("DISCOUNT CREATED")
                .build();
    }
}
