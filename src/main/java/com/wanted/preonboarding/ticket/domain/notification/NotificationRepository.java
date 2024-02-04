package com.wanted.preonboarding.ticket.domain.notification;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Integer> {

    List<Notification> findAllByTargetId(String targetId);
}
