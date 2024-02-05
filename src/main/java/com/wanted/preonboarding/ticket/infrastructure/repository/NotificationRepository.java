package com.wanted.preonboarding.ticket.infrastructure.repository;

import com.wanted.preonboarding.ticket.domain.entity.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface NotificationRepository extends JpaRepository<Notification, UUID> {
    List<Notification> findAllByPerformanceId(UUID performanceId);
}
