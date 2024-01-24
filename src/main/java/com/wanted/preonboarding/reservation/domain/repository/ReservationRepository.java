package com.wanted.preonboarding.reservation.domain.repository;

import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {
}
