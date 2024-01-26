package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor(access=AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
@Entity
public class PerformanceSeatInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "BINARY(16)", nullable = false, name="performance_id")
    @Comment("공연전시ID")
    private UUID performanceId;

    @Column(nullable = false)
    @Comment("회차")
    private int round;

    @Column(nullable = false)
    @Comment("입장 게이트")
    private int gate;

    @Column(nullable = false, length=2)
    @Comment("좌석 열")
    private char line;

    @Column(nullable = false)
    @Comment("좌석 행")
    private int seat;

    @Column(nullable = false, name="is_reverse", columnDefinition = "varchar default 'disable'")
    @Comment("예약 여부")
    private String isReverse;

}
