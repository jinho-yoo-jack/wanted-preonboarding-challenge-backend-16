package com.wanted.preonboarding.ticket.domain.dto.request;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
public class MailRequest {
    private String email;
    private UUID performanceId;
    private String performanceName;
    private int round;
    private int seat;
    private char line;
    private LocalDateTime startDate;

}
