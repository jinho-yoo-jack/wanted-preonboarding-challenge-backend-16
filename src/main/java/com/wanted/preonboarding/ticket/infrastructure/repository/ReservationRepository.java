package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    /**
     * name과 phoneNumber 필드가 매개변수 String name, String phoneNumber 과 같은 엔티티를 리턴
     * @param name
     * @param phoneNumber
     * @return Reservation
     */
    //Reservation findByNameAndPhoneNumber(String name, String phoneNumber);
}
