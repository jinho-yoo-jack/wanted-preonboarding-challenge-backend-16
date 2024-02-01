package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.entity.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface ReservationRepository extends JpaRepository<Reservation, Integer> {

    /**
     * Reservation의 연관 엔티티인 User 엔티티의 phoneNumber, name 필드를 통해 예약 정보 질의
     * @param name
     * @param phoneNumber
     * @return List<Reservation>
     */
    List<Reservation> findByUser_phoneNumberAndUser_name(String phoneNumber, String name);
}
