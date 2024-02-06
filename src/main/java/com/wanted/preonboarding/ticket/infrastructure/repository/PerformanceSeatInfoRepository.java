package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.PerformanceSeatInfo;

import java.util.Optional;
import java.util.UUID;

public interface PerformanceSeatInfoRepository {

    Optional<PerformanceSeatInfo> findById(Long id);

    Optional<PerformanceSeatInfo> findBySeatInfo(UUID performanceId, int round, char line, int gate, int seat);
}
