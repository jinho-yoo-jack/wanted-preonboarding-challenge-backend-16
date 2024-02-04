package com.wanted.preonboarding.domain.performance.service;

import com.wanted.preonboarding.domain.performance.domain.entity.Performance;
import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceStatus;
import com.wanted.preonboarding.domain.performance.repository.PerformanceRepository;
import com.wanted.preonboarding.domain.performance.repository.PerformanceRepositoryQueryDsl;

import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class PerformanceService {

    private final PerformanceRepository performanceRepository;
    private final PerformanceRepositoryQueryDsl performanceRepositoryQueryDsl;


    @Transactional(readOnly = true)
    public Performance getPerformance(UUID performanceId) {

        return this.performanceRepository.findById(performanceId)
            .orElseThrow();
    }

    @Transactional(readOnly = true)
    public List<Performance> getPerformance(List<UUID> performanceIdList) {

        return this.performanceRepository.findAllByIdIn(performanceIdList);
    }


    @Transactional
    public Page<Performance> getPerformancePage(
        List<PerformanceStatus> statusList,
        int page, int size) {

        return this.performanceRepositoryQueryDsl.findPage(statusList, page, size);
    }


}
