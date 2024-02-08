package com.wanted.preonboarding.ticket.repository;

import com.wanted.preonboarding.ticket.domain.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    List<Performance> findAllByIsReserve(Boolean isReserve);
}
