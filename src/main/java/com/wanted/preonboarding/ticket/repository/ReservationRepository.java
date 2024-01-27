package com.wanted.preonboarding.ticket.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    List<Reservation> findAllByName(final String name);
}
