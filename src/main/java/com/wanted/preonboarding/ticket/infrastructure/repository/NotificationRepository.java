package com.wanted.preonboarding.ticket.infrastructure.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.ticket.domain.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
