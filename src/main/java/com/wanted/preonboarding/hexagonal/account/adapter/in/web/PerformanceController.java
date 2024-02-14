package com.wanted.preonboarding.hexagonal.account.adapter.in.web;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.wanted.preonboarding.hexagonal.account.application.port.in.GetPerformanceInfoUseCase;
import com.wanted.preonboarding.hexagonal.account.domain.PerformanceInfo;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/performance/performance")
@RequiredArgsConstructor
public class PerformanceController {
	private final GetPerformanceInfoUseCase getPerformanceInfoUseCase;
	
	@GetMapping
	public List<PerformanceInfo> getAllPerformanceInfo() {
        return getPerformanceInfoUseCase.getPerformanceInfoAllList();
    }
}
