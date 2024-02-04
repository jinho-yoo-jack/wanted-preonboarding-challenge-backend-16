package com.wanted.preonboarding.domain.performance.domain.response;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.wanted.preonboarding.domain.common.domain.dto.PageDto;
import com.wanted.preonboarding.domain.performance.domain.dto.PerformanceDto;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;

@Getter
@JsonDeserialize(builder = PerformancePageResponse.JsonBuilder.class)
@Builder(builderClassName = "JsonBuilder", toBuilder = true, access = AccessLevel.PROTECTED)
public class PerformancePageResponse {
    @JsonPOJOBuilder(withPrefix = "")
    protected static class JsonBuilder {}

    private PageDto<PerformanceDto> performancePage;



    public static PerformancePageResponse of(
        PageDto<PerformanceDto> performancePage) {

        return PerformancePageResponse.builder()
            .performancePage(performancePage)
            .build();
    }

}
