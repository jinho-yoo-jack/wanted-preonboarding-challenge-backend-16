package com.wanted.preonboarding.ticket.repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.ReservationStatus;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {

    Optional<Performance> findById(final UUID id);

    List<Performance> findAllByStatus(final ReservationStatus status);
}
