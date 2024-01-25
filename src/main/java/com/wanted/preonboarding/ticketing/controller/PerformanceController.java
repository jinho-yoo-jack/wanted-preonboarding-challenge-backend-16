package com.wanted.preonboarding.ticketing.controller;

import com.wanted.preonboarding.ticketing.domain.dto.response.ReadPerformanceResponse;
import com.wanted.preonboarding.ticketing.service.PerformanceService;
import jakarta.validation.constraints.NotBlank;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Validated
@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceService performanceService;

    @GetMapping
    public ResponseEntity<Page<ReadPerformanceResponse>> readPerformance(@RequestParam @NotBlank String isReserve,
                                                                         @PageableDefault(sort = "id", direction = Sort.Direction.DESC) Pageable pageable) {
        return ResponseEntity.ok(performanceService.read(isReserve, pageable));
    }
}
