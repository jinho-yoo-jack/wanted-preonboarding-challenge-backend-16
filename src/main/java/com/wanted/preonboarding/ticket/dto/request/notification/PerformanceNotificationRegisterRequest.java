package com.wanted.preonboarding.ticket.dto.request.notification;

public record PerformanceNotificationRegisterRequest(
    String performanceId,
    String name,
    String phone
) {

}
