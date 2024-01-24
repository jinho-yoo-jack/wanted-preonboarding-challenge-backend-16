package com.wanted.preonboarding.reservation.domain.repository;

import com.wanted.preonboarding.reservation.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Long> {

    List<Reservation> findAllByNameAndPhoneNumber(final String name, final String phoneNumber);
}
