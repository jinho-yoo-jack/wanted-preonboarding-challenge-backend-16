package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.PerformanceService;
import com.wanted.preonboarding.ticket.presentation.dto.PerformanceResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/v1/performances")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping
    public ResponseEntity<ResponseHandler<List<PerformanceResponse>>> getPerformances(){
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<List<PerformanceResponse>>builder()
                        .statusCode(HttpStatus.OK)
                        .message("Success")
                        .data(performanceService.getPerformances().stream()
                                .map(PerformanceResponse::of)
                                .collect(Collectors.toList()))
                        .build()
                );
    }

    @GetMapping("/{performanceId}")
    public ResponseEntity<ResponseHandler<PerformanceResponse>> getPerformance(@PathVariable final UUID performanceId){
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<PerformanceResponse>builder()
                        .statusCode(HttpStatus.OK)
                        .message("Success")
                        .data(PerformanceResponse.of(performanceService.getPerformance(performanceId)))
                        .build()
                );
    }
}
