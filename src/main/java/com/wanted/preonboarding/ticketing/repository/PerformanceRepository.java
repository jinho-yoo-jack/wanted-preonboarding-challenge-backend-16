package com.wanted.preonboarding.ticketing.repository;

import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PerformanceRepository extends JpaRepository<Performance, UUID> {
    Page<Performance> findByIsReserve(String isReserve, Pageable pageable);
}
