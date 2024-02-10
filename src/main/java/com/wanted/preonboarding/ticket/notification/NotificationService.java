package com.wanted.preonboarding.ticket.notification;

import com.wanted.preonboarding.ticket.domain.dto.reservation.NotificationDto;
import com.wanted.preonboarding.ticket.domain.dto.reservation.NotificationResponseDto;
import java.util.UUID;

public interface NotificationService {
  NotificationResponseDto register(NotificationDto dto);
  boolean unregister(long notificationId);
  boolean notice(UUID performanceId);
}
