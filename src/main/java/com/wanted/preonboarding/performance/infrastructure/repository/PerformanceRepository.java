package com.wanted.preonboarding.performance.infrastructure.repository;

import com.wanted.preonboarding.performance.domain.Performance;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
}
