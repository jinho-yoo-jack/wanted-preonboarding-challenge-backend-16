package com.wanted.preonboarding.ticket.dto.request.notification;

import com.wanted.preonboarding.ticket.domain.notification.Notification;

public record SimpleTarget(
    String name,
    String phoneNumber
) {

    public static SimpleTarget of(Notification notification) {
        return new SimpleTarget(notification.getName(), notification.getPhoneNumber());
    }
}
