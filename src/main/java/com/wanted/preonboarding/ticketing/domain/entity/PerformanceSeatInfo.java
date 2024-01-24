package com.wanted.preonboarding.ticketing.domain.entity;

import com.wanted.preonboarding.ticketing.domain.dto.request.CreateReservationRequest;
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
public class PerformanceSeatInfo {
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

    @Column(nullable = false)
    @Comment("예약 가능 여부")
    private String isReserve;

    @Column(nullable = false)
    @Comment("생성 시간")
    private LocalDateTime createdAt;

    @Column(nullable = false)
    @Comment("업데이트 시간")
    private LocalDateTime updatedAt;

    public void updateReservationStatus(CreateReservationRequest createReservationRequest) {
        String requestStatus = createReservationRequest.getReservationStatus();
        if (!this.isReserve.equals(requestStatus)) {
            this.isReserve = requestStatus;
        }
    }
}
