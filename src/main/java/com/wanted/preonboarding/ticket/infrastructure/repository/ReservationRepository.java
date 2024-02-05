package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Reservation findByNameAndPhoneNumber(String name, String phoneNumber);

    @Query("SELECT new com.wanted.preonboarding.ticket.infrastructure.repository.FindReservationAndPerformanceDto(r.round, p.name, r.line, r.seat, r.performanceId, r.name, r.phoneNumber)" +
            " FROM Reservation r" +
            " INNER JOIN Performance p ON r.performanceId = p.id" +
            " WHERE r.name = :name AND r.phoneNumber = :phoneNumber")
    List<FindReservationAndPerformanceDto> findReservationAndPerformance(String name, String phoneNumber);
}
