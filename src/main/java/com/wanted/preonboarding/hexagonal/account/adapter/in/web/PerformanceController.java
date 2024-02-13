package com.wanted.preonboarding.hexagonal.account.adapter.in.web;

import com.wanted.preonboarding.hexagonal.account.application.port.in.GetPerformanceInfoUseCase;
import com.wanted.preonboarding.hexagonal.account.domain.PerformanceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/performance")
@RequiredArgsConstructor
public class PerformanceController {
    private final GetPerformanceInfoUseCase getPerformanceInfoUseCase;

    @GetMapping
    public List<PerformanceInfo> getAllPerformanceInfo() {
        return getPerformanceInfoUseCase.getPerformanceInfoAllList();
    }
}

