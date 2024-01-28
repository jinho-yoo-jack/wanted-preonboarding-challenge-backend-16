package com.wanted.preonboarding.ticket.application.performance.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.performance.service.PerformanceService;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceDetail;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.request.RegisterPerformance;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/performance")
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping("/list")
    public ResponseEntity<ResponseHandler<List<PerformanceInfo>>> getAllPerformanceInfoList() {
        return performanceService.getAllPerformanceInfoList();
    }

    @GetMapping("/detail/{id}/{round}")
    public ResponseEntity<ResponseHandler<PerformanceDetail>> getPerformanceInfoDetail(
            final @PathVariable UUID id,
            final @PathVariable int round
    ) {
        return performanceService.getPerformanceInfoDetail(id, round);
    }

    @PostMapping("/register")
    public ResponseEntity<ResponseHandler<PerformanceDetail>> registerPerformance(
            final @RequestBody RegisterPerformance request
    ) {
        return performanceService.registerPerformance(request);
    }


}
