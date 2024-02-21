package com.wanted.preonboarding.layered.service.ticket;

import com.wanted.preonboarding.domain.dto.reservation.NotificationDto;
import com.wanted.preonboarding.layered.repository.NotificationRepository;
import java.util.List;
import java.util.UUID;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class TicketOfficeV1 {
  private final NotificationRepository notificationRepository;

  public List<NotificationDto> getAllNotificationByPerformanceId(UUID uuid) {
    return this.notificationRepository.findAllByPerformanceId(uuid)
        .stream().map(
            NotificationDto::of
        ).toList();
  }
}
