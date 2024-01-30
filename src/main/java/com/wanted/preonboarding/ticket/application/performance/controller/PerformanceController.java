package com.wanted.preonboarding.ticket.application.performance.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.annotation.ExecutionTimer;
import com.wanted.preonboarding.ticket.application.performance.service.PerformanceService;
import com.wanted.preonboarding.ticket.domain.dto.request.RegisterPerformance;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceDetail;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

import static com.wanted.preonboarding.core.domain.response.ResponseHandler.MESSAGE_SUCCESS;
import static com.wanted.preonboarding.core.domain.response.ResponseHandler.createResponse;

@ExecutionTimer
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping("/list")
    public ResponseEntity<ResponseHandler<List<PerformanceInfo>>> getAllPerformanceInfoList() {
        List<PerformanceInfo> response = performanceService.getAllPerformanceInfoList();
        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, response);
    }

    @GetMapping("/{performance_id}/detail")
    public ResponseEntity<ResponseHandler<PerformanceDetail>> getPerformanceInfoDetail(
            @PathVariable("performance_id") final UUID id,
            @RequestParam final int round
    ) {
        PerformanceDetail response = performanceService.getPerformanceInfoDetail(id, round);
        return createResponse(HttpStatus.OK, MESSAGE_SUCCESS, response);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseHandler<PerformanceDetail>> registerPerformance(
            @RequestBody final RegisterPerformance request
    ) {
        PerformanceDetail response = performanceService.registerPerformance(request);
        return createResponse(HttpStatus.CREATED, MESSAGE_SUCCESS, response);
    }

}
