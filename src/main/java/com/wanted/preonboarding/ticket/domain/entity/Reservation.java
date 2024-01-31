package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.PerformanceInfo;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import com.wanted.preonboarding.user.domain.entity.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

/**
 * DB Table = reservation
 * 예약 정보 엔티티
 * private int id;  // 예약 정보 ID (PK)
 * private UUID performanceId;  // 공연전시ID
 * private String name; // 예약자명
 * private String phoneNumber;  // 예약자 휴대전화 번호
 * private int round;   // 회차(FK)
 * private int gate;    // 입장 게이트
 * private char line;   // 좌석 열
 * private int seat;    // 좌석 행
 */
@Entity
@Table(
    name = "reservation",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "reservation_round_row_seat",
            columnNames = {
                "performance_id",
                "round",
                "line",
                "seat"
            }
        )
    }
)
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@DynamicInsert  // insert시 지정하지 않은 값에 대하여 db default값이 적용되도록 하기 위함
@DynamicUpdate
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;  // 예약 정보 ID (PK)
//    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
//    private UUID performanceId; // 공연전시ID
//    @Column(nullable = false)
//    private int round;  // 회차(FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumns({
        @JoinColumn(name = "performance_id", referencedColumnName = "id", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT)),
        @JoinColumn(name = "round", referencedColumnName = "round", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    })  //(when an association has multiple '@JoinColumn's, they must each specify their 'referencedColumnName')
    private Performance performance;

    @Column(nullable = false)
    private String name;    // 예약자명

//    @Column(nullable = false, name = "phone_number")
//    private String phoneNumber; // 예약자 휴대전화 번호

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "phone_number", referencedColumnName = "phoneNumber", nullable = false, foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private User user;

    @Column(nullable = false)
    private int gate;   // 입장 게이트

    @Column(nullable = false)
    private char line;  // 좌석 열

    @Column(nullable = false)
    private int seat;   // 좌석 행

    @Column(nullable = true)
    private LocalDateTime createdAt;    // 생성 시각

    @Column(nullable = true)
    private LocalDateTime updatedAt;     // 수정 시각

    public static Reservation of(ReserveInfo info, Performance performance) {
        return Reservation.builder()
            .performance(performance)
            .name(info.getReservationName())
            .user(info.getUser())
            .gate(1)
            .line(info.getLine())
            .seat(info.getSeat())
            .build();
    }

}
