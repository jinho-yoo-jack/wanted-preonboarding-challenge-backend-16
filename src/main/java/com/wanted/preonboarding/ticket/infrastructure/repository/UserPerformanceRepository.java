package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.UserPerformance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface UserPerformanceRepository extends JpaRepository<UserPerformance, Long> {
    UserPerformance findByUserIdAndPerformanceId(Long userId, UUID performanceId);
    List<UserPerformance> findByPerformanceId(UUID performanceId);
}
