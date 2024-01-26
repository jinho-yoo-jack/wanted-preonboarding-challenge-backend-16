package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ApiResponse;
import com.wanted.preonboarding.ticket.application.PerformanceService;
import com.wanted.preonboarding.ticket.application.dto.response.PerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequiredArgsConstructor
@RequestMapping("/api/v1/performances")
@RestController
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping("/{performanceId}")
    public ResponseEntity<ApiResponse<PerformanceResponse>> findOne(@PathVariable UUID performanceId) {
        return ResponseEntity.ok(
                ApiResponse.ok(
                        performanceService.findOne(performanceId)
                )
        );
    }
}
