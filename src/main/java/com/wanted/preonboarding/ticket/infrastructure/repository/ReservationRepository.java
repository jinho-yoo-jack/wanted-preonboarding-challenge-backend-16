package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Optional<List<Reservation>> findByNameAndPhoneNumber(String name, String phoneNumber);

    void deleteByPerformanceIdAndNameAndPhoneNumberAndRound(
            UUID performanceId, String name, String phoneNumber, int round
    );
}
