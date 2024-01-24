package com.wanted.preonboarding.ticketing.domain.entity;

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
public class Performance {
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
    private int type;

    @Column(nullable = false)
    @Comment("공연 일시")
    private LocalDateTime startDate;

    @Column(nullable = false)
    @Comment("얘약 가능 여부")
    private String isReserve;

    @Column(nullable = false)
    @Comment("생성 시간")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Comment("업데이트 시간")
    private LocalDateTime updatedAt;

    public int calculateChange(int balance) {
        return balance - this.price;
    }

    public ReadPerformanceResponse toReadPerformanceResponse() {
        return ReadPerformanceResponse.builder()
                .performanceName(this.name)
                .round(this.round)
                .startDate(this.startDate)
                .isReserve(this.isReserve)
                .build();
    }
}
