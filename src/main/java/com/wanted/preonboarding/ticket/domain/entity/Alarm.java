package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.ReservePossibleAlarmCustomerInfoDto;
import com.wanted.preonboarding.ticket.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Alarm extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;//공연ID
    @Column(nullable = false)
    private String name; //이름
    @Column(nullable = false)
    private String phoneNumber; //연락처
    @Column(nullable = false)
    private String email; //이메일

    public static Alarm of (PerformanceSeatInfo performanceSeatInfo, ReservePossibleAlarmCustomerInfoDto dto) {
        return Alarm.builder()
                .performanceId(performanceSeatInfo.getPerformanceId())
                .name(dto.getReservationName())
                .phoneNumber(dto.getReservationPhoneNumber())
                .email(dto.getReservationEmail())
                .build();
    }
}
