package com.wanted.preonboarding.domain.performance.containerservice;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.wanted.preonboarding.domain.common.domain.dto.PageDto;
import com.wanted.preonboarding.domain.performance.domain.dto.PerformanceDto;
import com.wanted.preonboarding.domain.performance.domain.dto.PerformanceHallDto;
import com.wanted.preonboarding.domain.performance.domain.entity.Performance;
import com.wanted.preonboarding.domain.performance.domain.entity.PerformanceHall;
import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceStatus;
import com.wanted.preonboarding.domain.performance.domain.response.PerformanceDetailResponse;
import com.wanted.preonboarding.domain.performance.domain.response.PerformancePageResponse;
import com.wanted.preonboarding.domain.performance.service.PerformanceHallService;
import com.wanted.preonboarding.domain.performance.service.PerformanceService;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Service
public class PerformanceContainerService {

    private final PerformanceService performanceService;
    private final PerformanceHallService performanceHallService;


    @Transactional(readOnly = true)
    public PerformanceDetailResponse getPerformanceDetail(UUID performanceId) {

        Performance performance = this.performanceService.getPerformance(performanceId);
        PerformanceHall hall = this.performanceHallService.getPerformanceHall(performance.getHallId());

        // TODO information

        return PerformanceDetailResponse.of(
            performance,
            PerformanceHallDto.of(hall));
    }

    @Transactional(readOnly = true)
    public PerformancePageResponse getPerformancePage(
        List<PerformanceStatus> statusList,
        int page, int size) {

        Page<Performance> performancePage = this.performanceService
            .getPerformancePage(statusList, page, size);

        return PerformancePageResponse.of(
            PageDto.of(
                performancePage.stream().map(PerformanceDto::of).collect(Collectors.toList()),
                performancePage.getTotalElements(),
                performancePage.hasNext()));
    }


}
