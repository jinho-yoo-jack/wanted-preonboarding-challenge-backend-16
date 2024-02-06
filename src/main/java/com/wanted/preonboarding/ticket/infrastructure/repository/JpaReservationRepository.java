package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.infrastructure.dto.ReserveQueryResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface JpaReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepository {

    @Query("""
          SELECT new com.wanted.preonboarding.ticket.infrastructure.dto.ReserveQueryResponse(r.performanceId, p.name, r.round ,r.line, r.gate, r.seat, r.name, r.phoneNumber)
          FROM Reservation r
          JOIN Performance p ON p.id = r.performanceId
          WHERE r.name = :name AND r.phoneNumber = :phoneNumber
          """)
    @Override
    List<ReserveQueryResponse> findByNameAndPhoneNumber(String name, String phoneNumber);
}
