package com.wanted.preonboarding.domain.dto.reservation;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class NotificationResponseDto {
  private Integer notificationId;
  private String  userName;
  private String  phoneNumber;
  private String  userEmail;
}