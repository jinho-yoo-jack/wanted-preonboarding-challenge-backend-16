package com.wanted.preonboarding.performance.presentation.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.performance.application.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping("")
    public ResponseHandler<Object> findAllPerformanceByReserveState(@RequestParam String isReserve) {
        return ResponseHandler.builder()
                .statusCode(HttpStatus.OK)
                .data(performanceService.findPerformancesInReserveState(isReserve))
                .build();
    }
}
