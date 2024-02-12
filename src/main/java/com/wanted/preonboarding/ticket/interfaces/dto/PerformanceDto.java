package com.wanted.preonboarding.ticket.interfaces.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.wanted.preonboarding.ticket.domain.code.ActiveType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceDto {

    private UUID performanceId;
    private String name;
    private int round;
    @JsonFormat(pattern = "yyyy-MM-DD:HH:mm:ss")//todo 뭐더라?
    private LocalDateTime startDate;
    private ActiveType isReserve;
}
