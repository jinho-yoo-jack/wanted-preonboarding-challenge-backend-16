package com.wanted.preonboarding.ticketing.domain.dto.request;

import com.wanted.preonboarding.ticketing.domain.entity.Alarm;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateAlarmRequest {
    private final UUID performanceId;
    private final String customerName;
    private final String phoneNumber;

    public Alarm from(Performance performance) {
        return Alarm.builder()
                .performance(performance)
                .name(this.customerName)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
