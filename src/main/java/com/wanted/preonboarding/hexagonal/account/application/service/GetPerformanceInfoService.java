package com.wanted.preonboarding.hexagonal.account.application.service;

import java.util.List;

import org.springframework.stereotype.Component;

import com.wanted.preonboarding.hexagonal.account.application.port.in.GetPerformanceInfoUseCase;
import com.wanted.preonboarding.hexagonal.account.application.port.out.LoadPerformancePort;
import com.wanted.preonboarding.hexagonal.account.domain.PerformanceInfo;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class GetPerformanceInfoService implements GetPerformanceInfoUseCase{
	private final LoadPerformancePort loadPerformancePort;

	@Override
	public List<PerformanceInfo> getPerformanceInfoAllList() {
		 return loadPerformancePort.getAllPerformanceInfo();
	}

	
}
