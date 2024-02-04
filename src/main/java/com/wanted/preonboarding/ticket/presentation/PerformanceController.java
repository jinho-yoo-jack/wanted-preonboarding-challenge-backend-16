package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.application.PerformanceService;
import com.wanted.preonboarding.ticket.application.dto.PerformanceSearchCondition;
import com.wanted.preonboarding.ticket.application.dto.PerformanceDto;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/performances")
@RequiredArgsConstructor
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping
    public ResponseEntity<ResponseHandler<Page<PerformanceDto>>> getPerformances(PerformanceSearchCondition condition, Pageable pageable){
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<Page<PerformanceDto>>builder()
                        .statusCode(HttpStatus.OK)
                        .message("Success")
                        .data(performanceService.getPerformances(condition, pageable))
                        .build()
                );
    }

    @GetMapping("/{performanceId}")
    public ResponseEntity<ResponseHandler<PerformanceDto>> getPerformance(@PathVariable final UUID performanceId){
        return ResponseEntity
                .ok()
                .body(ResponseHandler.<PerformanceDto>builder()
                        .statusCode(HttpStatus.OK)
                        .message("Success")
                        .data(PerformanceDto.of(performanceService.getPerformance(performanceId)))
                        .build()
                );
    }
}
