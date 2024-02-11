package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Alarm;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface AlarmRepository extends JpaRepository<Alarm, Integer> {
    Optional<Alarm> findByPerformanceIdAndNameAndPhoneNumberAndEmail(UUID performanceId, String reservationName, String reservationPhoneNumber, String reservationEmail);
}
