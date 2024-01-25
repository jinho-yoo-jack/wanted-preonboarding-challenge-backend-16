package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.*;
import java.util.UUID;

@Entity
@Table
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 예약 ID

    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId; // 공연 정보 ID

    @Column(nullable = false)
    private String name; // 예약자 이름

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber; // 핸드폰 번호

    @Column(nullable = false)
    private int round; // 공연 회차

    private int gate; // 공연 입구
    private char line; // 줄
    private int seat; // 좌석 번호

    @Builder
    private Reservation(Long id, UUID performanceId, String name, String phoneNumber, int round,
                       int gate, char line, int seat) {
        this.id = id;
        this.performanceId = performanceId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.round = round;
        this.gate = gate;
        this.line = line;
        this.seat = seat;
    }

}
