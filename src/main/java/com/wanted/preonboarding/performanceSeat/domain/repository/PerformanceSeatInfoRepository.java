package com.wanted.preonboarding.performanceSeat.domain.repository;

import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performanceSeat.domain.entity.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Long> {

    Optional<PerformanceSeatInfo> findBySeatInfoAndPerformanceId(final SeatInfo seatInfo, final UUID performanceId);
}
