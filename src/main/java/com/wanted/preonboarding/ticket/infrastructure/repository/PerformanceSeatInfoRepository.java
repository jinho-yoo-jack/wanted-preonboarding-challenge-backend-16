package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Long> {
    @Query("select ps from PerformanceSeatInfo ps where ps.performanceId =:uuid and ps.isReserve = 'cancel'")
    Optional<PerformanceSeatInfo> findByUUID(UUID uuid);
}
