package com.wanted.preonboarding.performance.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.performance.domain.entity.Performance;

import java.util.List;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
    List<Performance> findByIsReserve(String isReserve);
    Performance findByName(String name);
}
