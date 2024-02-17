package com.wanted.preonboarding.domain.dto.reservation;

import com.wanted.preonboarding.domain.entity.Notification;
import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NotificationDto {
  private final UUID performanceId;
  private final String userName;
  private final String phoneNum;
  private final String email;

  public static NotificationDto of(Notification entity) {
    return NotificationDto.builder()
        .performanceId(entity.getPerformanceId())
        .userName(entity.getUser().getUserName())
        .phoneNum(entity.getUser().getPhoneNumber())
        .email(entity.getMail())
        .build();
  }
}