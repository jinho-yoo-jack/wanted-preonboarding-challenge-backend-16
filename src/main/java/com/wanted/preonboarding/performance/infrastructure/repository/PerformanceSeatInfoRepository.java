package com.wanted.preonboarding.performance.infrastructure.repository;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.performance.domain.entity.PerformanceSeatInfo;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {
    List<PerformanceSeatInfo> findByPerformanceId(UUID performanceId);
}
