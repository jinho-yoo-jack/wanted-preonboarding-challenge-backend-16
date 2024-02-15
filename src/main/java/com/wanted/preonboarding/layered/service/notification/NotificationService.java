package com.wanted.preonboarding.layered.service.notification;

import com.wanted.preonboarding.domain.dto.reservation.NotificationDto;
import com.wanted.preonboarding.domain.dto.reservation.NotificationResponseDto;
import java.util.UUID;

public interface NotificationService {
  NotificationResponseDto register(NotificationDto dto);
  boolean unregister(long notificationId);
  boolean notice(UUID performanceId);
}
