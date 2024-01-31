package com.wanted.preonboarding.reservation.framwork.jpaadapter.repository;

import com.wanted.preonboarding.reservation.domain.Reservation;
import com.wanted.preonboarding.reservation.domain.vo.NamePhone;
import java.util.Optional;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReservationRepository extends JpaRepository<Reservation, UUID> {

	Optional<Reservation> findByNamePhone(NamePhone namePhone);

}
