package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
    List<Performance> findByIsReserve(String isReserve);
    Optional<Performance> findByName(String name);

    Optional<Performance> findByIdAndRoundAndIsReserve(UUID id, int round, String isReserve);
}
