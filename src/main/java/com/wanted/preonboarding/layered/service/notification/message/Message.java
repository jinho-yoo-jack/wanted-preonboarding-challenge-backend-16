package com.wanted.preonboarding.layered.service.notification.message;

import com.wanted.preonboarding.domain.entity.SeatInfo;
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

  public static Message of(SeatInfo info) {
    return Message.builder()
        .performanceId(info.getPerformance().getId())
        .performanceName(info.getPerformance().getName())
        .round(info.getPerformance().getRound())
        .line(info.getLine())
        .seat(info.getSeat())
        .build();
  }
}
