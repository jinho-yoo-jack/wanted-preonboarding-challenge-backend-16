package com.wanted.preonboarding.ticket.contoller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.core.domain.response.ResponseHandler;
import com.wanted.preonboarding.ticket.service.PerformanceService;
import com.wanted.preonboarding.ticket.service.dto.request.PerformanceCheckRequestDto;
import com.wanted.preonboarding.ticket.service.dto.response.PerformanceCheckResponseDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performances")
@Slf4j
public class PerformanceController {

    private final PerformanceService performanceService;

    @GetMapping
    public ResponseEntity<ResponseHandler<List<PerformanceCheckResponseDto>>> getPerformances(
            @RequestBody final PerformanceCheckRequestDto request) {
        log.info("getPerformances request: {}", request);
        final List<PerformanceCheckResponseDto> performances = performanceService.getPerformances(request);
        return ResponseEntity.ok()
                .body(ResponseHandler.<List<PerformanceCheckResponseDto>>builder()
                        .message("Get Performances Success")
                        .statusCode(HttpStatus.OK)
                        .data(performances)
                        .build());
    }
}
