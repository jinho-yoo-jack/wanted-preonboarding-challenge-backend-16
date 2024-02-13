package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Builder
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
@Entity
public class PerformanceSeatInfo extends AuditInformation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", columnDefinition = "BINARY(16)", nullable = false)
    @Comment("공연전시ID")
    private Performance performance;

    @Column(nullable = false)
    @Comment("회차")
    private int round;

    @Column(nullable = false)
    @Comment("입장 게이트")
    private int gate;

    @Column(nullable = false, length = 2)
    @Comment("좌석 열")
    private char line;

    @Column(nullable = false)
    @Comment("좌석 행")
    private int seat;

    @Column(name = "is_reserve", nullable = false, columnDefinition = "varchar default 'disable'")
    @Comment("예약 여부")
    private String isReserve;

    //TODO: 파라미터로 status를 받을지 생각

    private static final String RESERVE_ENABLE = "enable";
    private static final String RESERVE_DISABLE = "disable";
    public void changeIsReserveStatus(){
        if (this.isReserve.equalsIgnoreCase(RESERVE_DISABLE)){
            isReserve = RESERVE_ENABLE;
        } else {
            isReserve = RESERVE_DISABLE;
        }
    }
}
