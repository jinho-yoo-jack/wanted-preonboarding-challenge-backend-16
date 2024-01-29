package com.wanted.preonboarding.ticket.application.reservation.repository;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    Optional<Reservation> findByCode(String code);

    boolean existsByCode(String code);
}
