package com.wanted.preonboarding.ticket.domain.dto.assembler;

import com.wanted.preonboarding.ticket.domain.dto.request.AddPerformance;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceInfoResponse;
import com.wanted.preonboarding.ticket.domain.dto.response.PerformanceType;
import com.wanted.preonboarding.ticket.domain.entity.Performance;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.util.Arrays;

import static com.wanted.preonboarding.core.domain.util.Value.PERFORMANCE_DISABLE;
import static com.wanted.preonboarding.core.domain.util.Value.PERFORMANCE_ENABLE;


@Component
public class PerformanceAssembler
{
    public Performance toPerformance(AddPerformance addPerformance)
    {
        return Performance.builder()
                .name(addPerformance.getName())
                .price(addPerformance.getPrice())
                .round(addPerformance.getRound())
                .type(addPerformance.getType())
                .start_date(Date.valueOf(addPerformance.getStart_date()))
                .isReserve(addPerformance.isReserve() ? PERFORMANCE_ENABLE : PERFORMANCE_DISABLE)
                .build();
    }

    public PerformanceInfoResponse toDto(Performance performance)
    {
        return PerformanceInfoResponse.builder()
                .performanceId(performance.getId())
                .performanceName(performance.getName())
                .performanceType(convertCodeToName(performance.getType()))
                .startDate(performance.getStart_date())
                .isReserve(performance.getIsReserve())
                .build();
    }
    private static String convertCodeToName(int code){
        return Arrays.stream(PerformanceType.values()).filter(value -> value.getCategory() == code)
                .findFirst()
                .orElse(PerformanceType.NONE)
                .name();
    }
}