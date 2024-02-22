package com.wanted.preonboarding.layered.service.ticket.discount;

import com.wanted.preonboarding.domain.dto.reservation.NotificationDto;
import com.wanted.preonboarding.domain.entity.Reservation;
import com.wanted.preonboarding.layered.service.ticket.Client;
import com.wanted.preonboarding.layered.service.ticket.Ticket;
import java.util.List;
import java.util.UUID;

public interface TicketOffice {
  List<NotificationDto> getAllNotificationByPerformanceId(UUID uuid);
  Reservation sellTo(Client client, Ticket ticket);
}
