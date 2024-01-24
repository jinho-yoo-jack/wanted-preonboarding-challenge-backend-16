package com.wanted.preonboarding.performanceSeat.domain.repository;

import com.wanted.preonboarding.performanceSeat.domain.entity.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Long> {
}
