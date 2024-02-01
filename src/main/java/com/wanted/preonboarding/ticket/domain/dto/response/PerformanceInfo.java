package com.wanted.preonboarding.ticket.domain.dto.response;

import com.wanted.preonboarding.ticket.domain.entity.Performance;
import com.wanted.preonboarding.ticket.domain.enums.PerformanceType;
import lombok.Builder;
import lombok.Value;

import java.util.Arrays;

import static com.wanted.preonboarding.ticket.application.common.util.TimeFormatter.convertToReadableFormat;

@Value
@Builder
public class PerformanceInfo {

    String name;
    Integer price;
    Integer round;
    String type;
    String startDate;
    String isReserve;

    public static PerformanceInfo of(Performance performance) {
        return PerformanceInfo.builder()
                .name(performance.getName())
                .price(performance.getPrice())
                .round(performance.getRound())
                .type(convertToName(performance.getType()))
                .startDate(convertToReadableFormat(performance.getStartDate()))
                .isReserve(performance.getIsReserve().name())
                .build();
    }

    public static String convertToName(PerformanceType type) {
        return Arrays.stream(PerformanceType.values())
                .filter(t -> t.isSameCategory(type))
                .findFirst()
                .orElse(PerformanceType.NONE)
                .name();
    }

}
