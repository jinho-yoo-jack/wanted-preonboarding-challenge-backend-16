package com.wanted.preonboarding.hexagonal.account.application.port.out;

import com.wanted.preonboarding.hexagonal.account.domain.PerformanceInfo;

import java.util.List;

public interface LoadPerformancePort {
    List<PerformanceInfo> getAllPerformanceInfo();
}
