package com.wanted.preonboarding.performance.repository;

import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.performance.domain.constant.ReserveStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, Long> {
    Page<Performance> findByReserveStatus(ReserveStatus reserveStatus, Pageable pageable);
    Performance findByName(String name);
}
