package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ApiResponse;
import com.wanted.preonboarding.ticket.application.PerformanceService;
import com.wanted.preonboarding.ticket.application.dto.response.PerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/performances")
@RestController
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping
    public ResponseEntity<ApiResponse<List<PerformanceResponse>>> findAvailablePerformances(@RequestParam String isReserved) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        performanceService.findAvailablePerformances(isReserved)
                )
        );
    }

    @GetMapping("/{performanceId}")
    public ResponseEntity<ApiResponse<PerformanceResponse>> findOne(@PathVariable UUID performanceId) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        performanceService.findOne(performanceId)
                )
        );
    }
}
