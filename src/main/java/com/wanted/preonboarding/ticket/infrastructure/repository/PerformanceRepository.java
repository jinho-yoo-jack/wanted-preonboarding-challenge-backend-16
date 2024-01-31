package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
    Optional<Performance> findById(UUID id);
    Optional<Performance> findByNameAndRound(String name, Integer round);
    List<Performance> findAll();
    List<Performance> findByIsReserve(String isReserve);
}
