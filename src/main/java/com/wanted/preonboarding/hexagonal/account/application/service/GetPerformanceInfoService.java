package com.wanted.preonboarding.hexagonal.account.application.service;

import com.wanted.preonboarding.hexagonal.account.application.port.in.GetPerformanceInfoUseCase;
import com.wanted.preonboarding.hexagonal.account.application.port.out.LoadPerformancePort;
import com.wanted.preonboarding.hexagonal.account.domain.PerformanceInfo;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class GetPerformanceInfoService implements GetPerformanceInfoUseCase {
    private final LoadPerformancePort loadPerformancePort;

    @Override
    public List<PerformanceInfo> getPerformanceInfoAllList() {
        return loadPerformancePort.getAllPerformanceInfo();
    }
}
