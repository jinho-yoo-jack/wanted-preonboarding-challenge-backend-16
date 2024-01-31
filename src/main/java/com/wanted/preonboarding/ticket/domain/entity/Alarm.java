package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.AlarmInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Alarm {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "user_id")
    private UUID userId;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;

    public static Alarm of(AlarmInfo info) {
        return Alarm.builder()
                .userId(info.getUserInfo().getUserId())
                .performanceId(info.getPerformanceInfo().getPerformanceId())
                .build();
    }
}
