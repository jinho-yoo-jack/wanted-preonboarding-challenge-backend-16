package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface JpaPerformanceRepository extends JpaRepository<Performance, UUID>, PerformanceRepository {

    @Query("""
           SELECT p
           FROM Performance p
           WHERE p.isReserve = :isReserve
           """)
    @Override
    List<Performance> findByIsReserve(String isReserve);
}
