package com.wanted.preonboarding.ticket.repository;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {

    Optional<Performance> findById(UUID id);

    Performance findByName(String name);
}
