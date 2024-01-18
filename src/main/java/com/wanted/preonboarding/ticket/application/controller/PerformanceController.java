package com.wanted.preonboarding.ticket.application.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.service.PerformanceService;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceDetail;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


}
