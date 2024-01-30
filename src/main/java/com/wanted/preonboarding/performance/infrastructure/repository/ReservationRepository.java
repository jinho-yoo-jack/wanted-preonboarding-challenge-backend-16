package com.wanted.preonboarding.performance.infrastructure.repository;

import com.wanted.preonboarding.performance.domain.PerformanceReservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends JpaRepository<PerformanceReservation, Integer> {
    PerformanceReservation findByNameAndPhoneNumber(String name, String phoneNumber);
}
