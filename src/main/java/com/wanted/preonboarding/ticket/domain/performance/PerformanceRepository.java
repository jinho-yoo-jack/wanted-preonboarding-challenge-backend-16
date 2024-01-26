package com.wanted.preonboarding.ticket.domain.performance;

import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {

}
