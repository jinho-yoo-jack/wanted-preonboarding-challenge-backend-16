package com.wanted.preonboarding.hexagonal.account.application.port.in;


import com.wanted.preonboarding.hexagonal.account.domain.PerformanceInfo;

import java.util.List;

public interface GetPerformanceInfoUseCase {
    List<PerformanceInfo> getPerformanceInfoAllList();
}
