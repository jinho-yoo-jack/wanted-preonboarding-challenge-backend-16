package com.wanted.preonboarding.domain.dto.reservation;

import com.wanted.preonboarding.domain.dto.UserDto;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NotificationDto {
  private UserDto user;
  private String  mail;
  private UUID    performanceId;
}