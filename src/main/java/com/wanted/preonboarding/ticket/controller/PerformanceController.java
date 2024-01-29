package com.wanted.preonboarding.ticket.controller;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.service.PerformanceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/performance")
public class PerformanceController {
    private final PerformanceService performanceService;

    /** 공연/전시 전체 내역 조회 **/
    @GetMapping(value = "")
    public List<PerformanceInfo> getAllPerformance() {
        return performanceService.getAllPerformance();
    }

    /** 예매 가능한 공연/전시 내역 조회 **/
    @GetMapping(value = "/enable")
    public List<PerformanceInfo> getAblePerformance() {
        return performanceService.getAblePerformance();
    }
}
