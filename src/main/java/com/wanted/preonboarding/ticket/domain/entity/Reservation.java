package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveFindResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends AuditInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    @Comment("공연전시ID")
    private Performance performance;

    @Column(nullable = false)
    @Comment("예약자명")
    private String name;

    @Column(nullable = false, name = "phone_number")
    @Comment("예약자 휴대전화 번호")
    private String phoneNumber;

    @Column(nullable = false)
    @Comment("회차")
    private int round;

    @Column(nullable = false)
    @Comment("입장 게이트")
    private int gate;

    @Column(nullable = false)
    @Comment("좌석 열")
    private char line;

    @Comment("좌석 행")
    private int seat;

    // TODO: performance를 넣어도 되는것일까
    public static Reservation of(ReserveInfo info, Performance performance) {
        return Reservation.builder()
                .performance(performance)
                .name(info.getReservationName())
                .phoneNumber(info.getReservationPhoneNumber())
                .round(info.getRound())
                .gate(1)
                .line(info.getLine())
                .seat(info.getSeat())
                .build();
    }

    public ReserveFindResponse toReserveSelectResponse() {
        return ReserveFindResponse.builder()
                .performanceId(this.performance.getId())
                .performanceName(this.performance.getName())
                .round(this.round)
                .seat(this.seat)
                .line(String.valueOf(this.line))
                .reservationName(this.name)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
