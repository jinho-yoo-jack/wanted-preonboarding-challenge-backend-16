package com.wanted.preonboarding.ticket.dto.response.notification;

import com.wanted.preonboarding.ticket.domain.notification.Notification;
import lombok.Builder;

@Builder
public record NotificationRegisterResponse(
    int id,
    String targetId,
    String name,
    String phone
) {

    public static NotificationRegisterResponse of(final Notification notification) {
        return NotificationRegisterResponse.builder()
            .id(notification.getId())
            .targetId(notification.getTargetId())
            .name(notification.getName())
            .phone(notification.getPhoneNumber())
            .build();
    }

}
