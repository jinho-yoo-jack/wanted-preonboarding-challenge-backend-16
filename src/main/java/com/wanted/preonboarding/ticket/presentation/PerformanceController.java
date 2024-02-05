package com.wanted.preonboarding.ticket.presentation;

import com.wanted.preonboarding.ticket.application.PerformanceApp;
import com.wanted.preonboarding.ticket.domain.dto.CreatedPerformanceRequestDto;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final PerformanceApp performanceApp;

    @PostMapping("/")
    public void createdPerformance(CreatedPerformanceRequestDto requestDto){
        performanceApp.create(requestDto);
    }
    @GetMapping("/")
    public PerformanceInfo getPerformanceDetail(String performanceName){
        return performanceApp.getDetail(performanceName);
    }



}
