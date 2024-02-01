package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.enums.ReservationAvailability;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Getter
@Builder
@Comment("공연/전시 좌석 정보")
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "performance_seat_info", uniqueConstraints = {
        @UniqueConstraint(name = "performance_seat_info_uk", columnNames = {"performance_id", "round", "line", "seat"})
})
public class PerformanceSeatInfo extends BaseEntity {

    @Id
    @Comment("공연/전시 좌석 정보 ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Long id;

    @Comment("공연/전시 ID")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", nullable = false)
    private Performance performance;

    @Comment("회차")
    @JoinColumn(name = "round", nullable = false)
    private Integer round;

    @Comment("입장 게이트")
    @Column(name = "gate", nullable = false)
    private Integer gate;

    @Comment("좌석 열")
    @Column(name = "line", nullable = false, length = 2)
    private String line;

    @Comment("좌석 행")
    @Column(name = "seat", nullable = false)
    private Integer seat;

    @Comment("예약 가능 여부")
    @Enumerated(EnumType.STRING)
    @Column(name = "is_reserve", nullable = false, columnDefinition = "varchar(20) default 'AVAILABLE'")
    private ReservationAvailability isReserve;

    public void modifyReservationAvailability(ReservationAvailability reservationAvailability) {
        this.isReserve = reservationAvailability;
    }

    public static PerformanceSeatInfo of(Performance performance, String line, Integer seat) {
        return PerformanceSeatInfo.builder()
                .performance(performance)
                .round(performance.getRound())
                .gate(1)
                .line(line)
                .seat(seat)
                .isReserve(ReservationAvailability.AVAILABLE)
                .build();
    }

}
