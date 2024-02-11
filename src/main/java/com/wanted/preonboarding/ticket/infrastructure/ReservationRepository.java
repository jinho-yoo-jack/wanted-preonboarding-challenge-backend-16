package com.wanted.preonboarding.ticket.infrastructure;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Optional<Reservation> findByReservationHolderNameAndPhoneNumber(String reservationHolderName, String phoneNumber);
}
