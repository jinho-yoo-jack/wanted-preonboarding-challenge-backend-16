package com.wanted.preonboarding.layered.service.notification.message;

import java.util.UUID;
import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class Message {
  private UUID          performanceId;
  private final String  performanceName;
  private final int     round;
  private final String  line;
  private final int     seat;
}
