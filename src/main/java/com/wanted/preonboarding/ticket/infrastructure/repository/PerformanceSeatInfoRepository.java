package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;
import java.util.UUID;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Long> {
    @Query("select ps from PerformanceSeatInfo ps where ps.performanceId =:performanceId and ps.isReserve =:isReserve")
    Optional<PerformanceSeatInfo> findByPerformanceIdAndIsReserve(UUID performanceId, String isReserve);
}
