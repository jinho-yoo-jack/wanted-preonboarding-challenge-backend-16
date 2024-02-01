package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import com.wanted.preonboarding.ticket.domain.mapper.ReservationMapping;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    List<ReservationMapping> findByNameAndPhoneNumber(String name, String phoneNumber);
    @Modifying
    @Query("DELETE FROM Reservation r " +
            "WHERE r.performanceId = :performanceId" +
            "  AND r.seat = :seat")
    void deleteByPerformanceIdAndSeat(@Param("performanceId") UUID performanceId, @Param("seat") int seat);
}
