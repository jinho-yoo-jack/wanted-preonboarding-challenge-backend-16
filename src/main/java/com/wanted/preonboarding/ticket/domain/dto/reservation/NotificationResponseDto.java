package com.wanted.preonboarding.ticket.domain.dto.reservation;

import com.wanted.preonboarding.ticket.domain.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NotificationResponseDto {
  private UserDto user;
  private long    notificationId;
}