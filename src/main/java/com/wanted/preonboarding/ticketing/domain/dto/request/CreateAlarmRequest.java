package com.wanted.preonboarding.ticketing.domain.dto.request;

import com.wanted.preonboarding.ticketing.domain.entity.Alarm;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import jakarta.validation.constraints.NotBlank;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateAlarmRequest {
    @NotBlank
    private final UUID performanceId;
    @NotBlank
    private final String customerName;
    @NotBlank
    private final String phoneNumber;
    @NotBlank
    private final String email;

    public Alarm from(Performance performance) {
        return Alarm.builder()
                .performance(performance)
                .name(this.customerName)
                .phoneNumber(this.phoneNumber)
                .email(this.email)
                .build();
    }
}
