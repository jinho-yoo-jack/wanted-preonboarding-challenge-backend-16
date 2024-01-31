package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceSeatInfoRepository extends JpaRepository<PerformanceSeatInfo, Integer> {

    Optional<PerformanceSeatInfo> findById(Integer id);

    Optional<List<PerformanceSeatInfo>> findByIsReserveAndPerformance_id(String isReserve, UUID performanceId);

    Optional<PerformanceSeatInfo> findBySeatAndLineAndPerformance_idAndPerformance_round(int seat, char line, UUID performanceId, int round);
}
