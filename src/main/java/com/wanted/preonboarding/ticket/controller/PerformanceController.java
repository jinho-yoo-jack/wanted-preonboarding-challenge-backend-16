package com.wanted.preonboarding.ticket.controller;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.controller.model.PerformanceSelectModel;
import com.wanted.preonboarding.ticket.domain.Performance;
import com.wanted.preonboarding.ticket.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/performances")
public class PerformanceController {
    private final PerformanceService performanceService;

    @GetMapping
    public ResponseEntity<ResponseHandler<List<PerformanceSelectModel>>> getPerformances(@RequestParam boolean isReserve) {
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<List<PerformanceSelectModel>>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(performanceService.getPerformances(isReserve))
                        .build()
                );
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseHandler<PerformanceSelectModel>> getPerformances(@PathVariable Long id) {
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<PerformanceSelectModel>builder()
                        .message("Success")
                        .statusCode(HttpStatus.OK)
                        .data(performanceService.getPerformance(id))
                        .build()
                );
    }
}
