package com.wanted.preonboarding.hexagonal.account.application.port.out;

import java.util.List;

import com.wanted.preonboarding.hexagonal.account.domain.PerformanceInfo;


public interface LoadPerformancePort {
	List<PerformanceInfo> getAllPerformanceInfo();
}
