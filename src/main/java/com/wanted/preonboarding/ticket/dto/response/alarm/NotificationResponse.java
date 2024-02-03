package com.wanted.preonboarding.ticket.dto.response.alarm;

import com.wanted.preonboarding.ticket.domain.notification.Notification;
import lombok.Builder;

@Builder
public record NotificationResponse(
    int id,
    String targetId,
    String name,
    String phone
) {

    public static NotificationResponse of(final Notification notification) {
        return NotificationResponse.builder()
            .id(notification.getId())
            .targetId(notification.getTargetId())
            .name(notification.getName())
            .phone(notification.getPhoneNumber())
            .build();
    }

}
