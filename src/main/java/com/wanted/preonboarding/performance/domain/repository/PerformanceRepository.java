package com.wanted.preonboarding.performance.domain.repository;

import com.wanted.preonboarding.performance.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
}
