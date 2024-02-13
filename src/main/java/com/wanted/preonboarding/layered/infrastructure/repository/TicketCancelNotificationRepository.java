package com.wanted.preonboarding.layered.infrastructure.repository;

import com.wanted.preonboarding.layered.domain.entity.notification.TicketCancelNotification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TicketCancelNotificationRepository extends JpaRepository<TicketCancelNotification, Integer> {
    List<TicketCancelNotification> findAllByPerformanceId(UUID performanceId);
}
