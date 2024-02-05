package com.wanted.preonboarding.ticket.service;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.ticket.domain.entity.Notification;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}
