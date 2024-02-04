package com.wanted.preonboarding.domain.reservation.domain.entity;

import java.time.ZonedDateTime;
import java.util.UUID;

import org.hibernate.annotations.GenericGenerator;

import com.wanted.preonboarding.domain.performance.domain.enums.PerformanceType;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "reservation_notification")
@Getter
@Builder(access = AccessLevel.PROTECTED)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "performance_id", columnDefinition = "BINARY(16) NOT NULL COMMENT '공연/전시 ID'")
    private UUID performanceId;
    @Column(name = "hall_seat_id", columnDefinition = "bigint unsigned NOT NULL COMMENT '공연/전시장 좌석 ID'")
    private Long hallSeatId;

    // 예약자 정보
    @Column(name = "name", columnDefinition = "varchar(255) NOT NULL COMMENT '예약자 이름'")
    private String name;
    @Column(name = "phone_number", columnDefinition = "varchar(255) NOT NULL COMMENT '예약자 전화번호'")
    private String phoneNumber;

    @Column(name = "send_at", columnDefinition = "DATETIME NULL COMMENT '전송 일시'")
    private ZonedDateTime sendAt;



    public static ReservationNotification of(
        UUID performanceId,
        Long hallSeatId,

        String name,
        String phoneNumber) {

        return ReservationNotification.builder()
            .performanceId(performanceId)
            .hallSeatId(hallSeatId)
            .name(name)
            .phoneNumber(phoneNumber)
            .build();
    }
}
