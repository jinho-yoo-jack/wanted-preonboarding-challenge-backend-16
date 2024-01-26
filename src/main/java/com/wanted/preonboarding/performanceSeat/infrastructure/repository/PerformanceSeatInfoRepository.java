package com.wanted.preonboarding.performanceSeat.infrastructure.repository;

import com.wanted.preonboarding.common.model.PerformanceId;
import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.performanceSeat.domain.entity.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Long> {

    Optional<PerformanceSeatInfo> findBySeatInfoAndPerformanceId(final SeatInfo seatInfo, final PerformanceId performanceId);

    boolean existsByReserveStateAndPerformanceId(final String reserveState, final PerformanceId performanceId);

    List<PerformanceSeatInfo> findAllByPerformanceId(final PerformanceId performanceId);
}
