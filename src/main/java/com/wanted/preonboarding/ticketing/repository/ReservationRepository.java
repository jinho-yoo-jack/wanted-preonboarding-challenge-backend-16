package com.wanted.preonboarding.ticketing.repository;

import com.wanted.preonboarding.ticketing.domain.entity.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    Page<Reservation> findByPhoneNumberAndName(String phoneNumber, String reservationName, Pageable pageable);
}
