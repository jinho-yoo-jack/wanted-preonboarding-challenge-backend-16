package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;
import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation extends AuditInformation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    @Comment("공연전시ID")
    private UUID performanceId;

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

    public static Reservation of(ReserveInfo info) {
        return Reservation.builder()
                .performanceId(info.getPerformanceId())
                .name(info.getReservationName())
                .phoneNumber(info.getReservationPhoneNumber())
                .round(info.getRound())
                .gate(1)
                .line(info.getLine())
                .seat(info.getSeat())
                .build();
    }

}
