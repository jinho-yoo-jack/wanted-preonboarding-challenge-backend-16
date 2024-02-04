package com.wanted.preonboarding.domain.reservation.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.domain.reservation.domain.entity.ReservationNotification;

public interface ReservationNotificationRepository extends JpaRepository<ReservationNotification, Long> {

}
