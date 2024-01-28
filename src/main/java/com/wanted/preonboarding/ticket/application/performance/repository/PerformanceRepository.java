package com.wanted.preonboarding.ticket.application.performance.repository;

import com.wanted.preonboarding.ticket.domain.enums.ReservationAvailability;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface PerformanceRepository extends JpaRepository<Performance, UUID> {

    Optional<Performance> findByIdAndRound(UUID id, int round);

    List<Performance> findAllByIsReserve(ReservationAvailability isReserve);

}
