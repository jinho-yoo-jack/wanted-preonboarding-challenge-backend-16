package com.wanted.preonboarding.ticketing.domain.entity;

import com.wanted.preonboarding.ticketing.aop.advice.exception.SoldOutException;
import com.wanted.preonboarding.ticketing.aop.advice.payload.ErrorCode;
import com.wanted.preonboarding.ticketing.service.dto.email.EmailPerformanceSeatInfo;
import com.wanted.preonboarding.ticketing.controller.response.CancelReservationResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
@Getter
public class PerformanceSeatInfo extends Time {
    private static final String POSSIBLE = "enable";
    private static final String IMPOSSIBLE = "disable";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", columnDefinition = "BINARY(16)", nullable = false)
    @Comment("공연전시ID")
    private Performance performance;

    @Column(nullable = false)
    @Comment("회차(FK)")
    private int round;

    @Column(nullable = false)
    @Comment("입장 게이트")
    private int gate;

    @Column(length = 2, nullable = false)
    @Comment("좌석 열")
    private String line;

    @Column(nullable = false)
    @Comment("좌석 행")
    private int seat;

    @Column(nullable = false, columnDefinition = "VARCHAR(255) default 'enable'")
    @Comment("예약 가능 여부")
    private String isReserve;

    public void updateReservationStatus(String requestStatus) {
        if (this.isReserve.equals(requestStatus)) {
            throw new SoldOutException(ErrorCode.SOLD_OUT_TICKET);
        }
        this.isReserve = requestStatus;
    }

    public void updateCancelStatus() {
        if (this.isReserve.equals(IMPOSSIBLE)) {
            this.isReserve = POSSIBLE;
        }
    }

    public CancelReservationResponse toCancelReservationResponse() {
        return CancelReservationResponse.builder()
                .seatId(this.id)
                .isReserve(this.isReserve)
                .build();
    }

    public EmailPerformanceSeatInfo from() {
        return EmailPerformanceSeatInfo.builder()
                .id(this.id)
                .gate(this.gate)
                .line(this.line)
                .seat(this.seat)
                .build();
    }
}
