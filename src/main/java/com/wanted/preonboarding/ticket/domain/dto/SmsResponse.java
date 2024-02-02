package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.global.dto.BaseResDto;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;

@Getter @Setter
@SuperBuilder
public class SmsResponse extends BaseResDto {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
}