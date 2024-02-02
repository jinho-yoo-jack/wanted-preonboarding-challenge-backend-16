package com.wanted.preonboarding.ticket.dto.response.alarm;

import com.wanted.preonboarding.ticket.domain.performance.PerformanceAlarm;
import lombok.Builder;

@Builder
public record AlarmResponse(
    int id,
    String targetId,
    String name,
    String phone
) {

    public static AlarmResponse of(final PerformanceAlarm alarm) {
        return AlarmResponse.builder()
            .id(alarm.getId())
            .targetId(alarm.getPerformanceId().toString())
            .name(alarm.getName())
            .phone(alarm.getPhoneNumber())
            .build();
    }

}
