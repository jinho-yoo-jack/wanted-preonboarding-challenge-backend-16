package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface JpaPerformanceRepository extends JpaRepository<Performance, UUID>, PerformanceRepository {
}
