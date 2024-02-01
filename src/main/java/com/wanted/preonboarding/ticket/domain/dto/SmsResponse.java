package com.wanted.preonboarding.ticket.domain.dto;

import com.wanted.preonboarding.ticket.global.dto.BaseResDto;
import lombok.*;

import java.time.LocalDateTime;

@Builder
@Getter @Setter
public class SmsResponse extends BaseResDto {
    private String requestId;
    private LocalDateTime requestTime;
    private String statusCode;
    private String statusName;
}