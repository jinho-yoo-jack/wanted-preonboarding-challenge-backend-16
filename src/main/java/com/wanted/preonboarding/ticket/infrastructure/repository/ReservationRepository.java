package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.dto.ReserveQueryResponse;

import java.util.List;
import java.util.Optional;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    Optional<Reservation> findById(Long reservationId);

    List<ReserveQueryResponse> findByNameAndPhoneNumber(String name, String phoneNumber);
}
