package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.Performance;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;


public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
}
