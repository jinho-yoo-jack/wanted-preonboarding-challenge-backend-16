package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.core.code.ActiveType;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, PerformanceId> {
    List<Performance> findAllByIsReserve(ActiveType isReserve);

    Optional<Performance> findByIdId(UUID performanceId);

    Optional<Performance> findByIdAndIsReserve(PerformanceId performanceId, ActiveType isReserve);
}
