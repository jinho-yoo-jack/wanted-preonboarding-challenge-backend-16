package com.wanted.preonboarding.ticket.infrastructure.repository;


import com.wanted.preonboarding.ticket.domain.entity.Performance;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PerformanceRepository {

    Performance save(Performance performance);

    List<Performance> findByIsReserve(String isReserve);

    Optional<Performance> findById(UUID performanceId);
}
