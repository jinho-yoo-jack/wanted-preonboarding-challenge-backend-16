package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.dto.ReserveQueryResponse;

import java.util.List;

public interface ReservationRepository {

    Reservation save(Reservation reservation);

    List<ReserveQueryResponse> findByNameAndPhoneNumber(String name, String phoneNumber);
}
