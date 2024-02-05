package com.wanted.preonboarding.ticket.repository;

import com.wanted.preonboarding.ticket.domain.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReservationRepository extends JpaRepository<Reservation, Long> {
    List<Reservation> findAllByUserNameAndPhoneNumber(String userName, String phoneNumber);
    Optional<Reservation> findByIdAndUserNameAndPhoneNumber(Long id, String userName, String phoneNumber);
}
