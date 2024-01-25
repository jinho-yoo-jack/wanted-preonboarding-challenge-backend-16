package com.wanted.preonboarding.ticketing.domain.entity;

import com.wanted.preonboarding.ticketing.domain.dto.PerformanceType;
import com.wanted.preonboarding.ticketing.domain.dto.email.EmailPerformance;
import com.wanted.preonboarding.ticketing.domain.dto.response.ReadPerformanceResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
@Getter
public class Performance extends Time {
    @Id
    @GeneratedValue(generator = "uuid2")
    @Column(columnDefinition = "BINARY(16)", nullable = false)
    @Comment("공연/전시 ID")
    private UUID id;

    @Column(nullable = false)
    @Comment("공연/전시 이름")
    private String name;

    @Column(nullable = false)
    @Comment("가격")
    private int price;

    @Column(nullable = false)
    @Comment("회차")
    private int round;

    @Column(nullable = false)
    @Comment("NONE, CONCERT, EXHIBITION")
    private PerformanceType type;

    @Column(nullable = false)
    @Comment("공연 일시")
    private LocalDateTime startDate;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) default 'disable'")
    @Comment("예약 가능 여부")
    private String isReserve;

    public ReadPerformanceResponse toReadPerformanceResponse() {
        return ReadPerformanceResponse.builder()
                .performanceName(this.name)
                .round(this.round)
                .startDate(this.startDate)
                .isReserve(this.isReserve)
                .build();
    }

    public boolean isAffordable(int balance) {
        return this.price <= balance;
    }

    public EmailPerformance from() {
        return EmailPerformance.builder()
                .id(this.id)
                .name(this.name)
                .round(this.round)
                .startDate(this.startDate)
                .build();
    }
}
