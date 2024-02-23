package com.wanted.preonboarding.layered.service.ticket;

import com.wanted.preonboarding.domain.dto.reservation.NotificationDto;
import com.wanted.preonboarding.domain.entity.Reservation;
import java.util.List;
import java.util.UUID;

public interface TicketOffice {
  Reservation sellTo(Client client, Ticket ticket);

  List<NotificationDto> getSubscribers(UUID performanceId);

  int setSubscribers(NotificationDto dto);

  void delSubscriber(Integer id);
}
