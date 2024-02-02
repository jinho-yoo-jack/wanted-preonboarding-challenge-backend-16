package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {
    Reservation findByNameAndPhoneNumberAndStatus(String name, String phoneNumber, String status);
    @Transactional
    @Modifying
    @Query(value = "UPDATE reservation SET status = 'canceled' WHERE id = ?", nativeQuery = true)
    int updateStatus(int id);
}
