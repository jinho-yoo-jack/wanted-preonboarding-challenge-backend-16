package com.wanted.preonboarding.ticket.infrastructure.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.wanted.preonboarding.ticket.domain.entity.Notification;
import com.wanted.preonboarding.ticket.domain.entity.Performance;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
	List<Notification> findByPerformance(Performance performance);
}
