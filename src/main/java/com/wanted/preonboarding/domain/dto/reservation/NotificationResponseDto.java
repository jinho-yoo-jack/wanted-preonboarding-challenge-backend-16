package com.wanted.preonboarding.domain.dto.reservation;

import com.wanted.preonboarding.domain.dto.UserDto;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NotificationResponseDto {
  private UserDto user;
  private long    notificationId;
}