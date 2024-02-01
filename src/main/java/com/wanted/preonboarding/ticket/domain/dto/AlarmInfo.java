package com.wanted.preonboarding.ticket.domain.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Builder
public class AlarmInfo {

    // 공연ID
    @JsonProperty("performance_id")
    private UUID performanceId;

    // 사용자명
    @JsonProperty("name")
    private String name;

    // 예약 가능 알림 여부
    @JsonProperty("notice_yn")
    private String noticeYn;
}
