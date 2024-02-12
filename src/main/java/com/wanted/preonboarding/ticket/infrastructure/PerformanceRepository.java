package com.wanted.preonboarding.ticket.infrastructure;

import com.wanted.preonboarding.ticket.domain.code.ActiveType;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.PerformanceId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, PerformanceId> {
    List<Performance> findAllByIsReserve(ActiveType isReserve);

    Performance findByIdId(UUID performanceId);
}
