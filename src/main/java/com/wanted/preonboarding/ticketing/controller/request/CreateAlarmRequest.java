package com.wanted.preonboarding.ticketing.controller.request;

import com.wanted.preonboarding.ticketing.domain.entity.Alarm;
import com.wanted.preonboarding.ticketing.domain.entity.Performance;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class CreateAlarmRequest {
    @NotNull(message = "공연ID은 필수 입력 사항입니다.")
    private final UUID performanceId;
    @NotBlank(message = "고객 이름은 필수 입력 사항입니다.")
    private final String customerName;
    @NotBlank(message = "핸드폰 번호는 필수 입력 사항입니다.")
    private final String phoneNumber;
    @Email(message = "정확한 이메일 정보를 입력해주세요.")
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
