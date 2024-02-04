package com.wanted.preonboarding.ticket.domain.reservation;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<Reservation, Integer>, ReservationSearchRepository {

    List<Reservation>findReservationsByNameAndPhoneNumber(String name, String phoneNumber);
}
