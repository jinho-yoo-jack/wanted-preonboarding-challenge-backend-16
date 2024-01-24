package com.wanted.preonboarding.ticket.repository;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
//    List<Performance> findByIsReserve(String isReserve);
    Performance findByName(String name);
}
