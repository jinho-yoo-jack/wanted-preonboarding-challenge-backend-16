package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Reservation findByNameAndPhoneNumber(String name, String phoneNumber);

    @Query("select r from Reservation r where r.performanceId =:id")
    Reservation findByUUID(UUID id);
}
