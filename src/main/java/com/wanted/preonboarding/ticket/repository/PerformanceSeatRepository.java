package com.wanted.preonboarding.ticket.repository;

import com.wanted.preonboarding.ticket.domain.PerformanceSeat;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PerformanceSeatRepository extends JpaRepository<PerformanceSeat, Long> {
}
