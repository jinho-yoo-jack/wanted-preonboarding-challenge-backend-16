package com.wanted.preonboarding.ticket.dto.request.alarm;

public record PerformanceAlarmRequest (
    String performanceId,
    String name,
    String phone
) {

}
