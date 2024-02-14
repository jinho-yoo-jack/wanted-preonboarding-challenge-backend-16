package com.wanted.preonboarding.hexagonal.account.application.port.in;

import java.util.List;

import com.wanted.preonboarding.hexagonal.account.domain.PerformanceInfo;


public interface GetPerformanceInfoUseCase {
	List<PerformanceInfo> getPerformanceInfoAllList();
}
