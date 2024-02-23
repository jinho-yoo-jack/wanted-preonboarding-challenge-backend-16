package com.wanted.preonboarding.layered.repository;

import com.wanted.preonboarding.domain.entity.Notification;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {
  List<Notification> findAllByPerformanceId(UUID id);
}
