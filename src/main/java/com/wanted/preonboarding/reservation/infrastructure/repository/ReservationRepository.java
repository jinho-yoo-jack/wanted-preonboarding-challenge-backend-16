package com.wanted.preonboarding.reservation.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.reservation.domain.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<Reservation> findByNameAndPhoneNumber(String name, String phoneNumber);
}
