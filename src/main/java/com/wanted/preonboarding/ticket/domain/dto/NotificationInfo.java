package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.domain.entity.Notification;
import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
public class NotificationInfo {
    private Long id;
    private Long userId;
    private UUID performanceId;

    public static NotificationInfo of(Notification notification) {
        return NotificationInfo.builder()
                .id(notification.getId())
                .userId(notification.getUserId())
                .performanceId(notification.getPerformanceId())
                .build();
    }
}
