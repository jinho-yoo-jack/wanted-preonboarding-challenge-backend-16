package com.wanted.preonboarding.ticketing.domain.entity;

import com.wanted.preonboarding.ticketing.domain.dto.response.ReservationResponse;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

import java.time.LocalDateTime;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", columnDefinition = "BINARY(16)", nullable = false)
    @Comment("공연전시ID")
    private Performance performance;

    @Column(nullable = false)
    @Comment("예약자명")
    private String name;

    @Column(nullable = false)
    @Comment("예약자 휴대전화 번호")
    private String phoneNumber;

    @Column(nullable = false)
    @Comment("회차(FK)")
    private int round;

    @Column(nullable = false)
    @Comment("입장 게이트")
    private int gate;

    @Column(nullable = false)
    @Comment("좌석 열")
    private String line;

    @Column(nullable = false)
    @Comment("좌석 행")
    private int seat;

    @Column(nullable = false)
    @Comment("생성 시간")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Comment("업데이트 시간")
    private LocalDateTime updatedAt;

    public ReservationResponse toCreateReservationResponse(Performance performance, int changes) {
        return ReservationResponse.builder()
                .performanceId(this.performance.getId())
                .performanceName(performance.getName())
                .changes(changes)
                .round(this.round)
                .gate(this.gate)
                .line(this.line)
                .seat(this.seat)
                .reservationName(this.name)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
