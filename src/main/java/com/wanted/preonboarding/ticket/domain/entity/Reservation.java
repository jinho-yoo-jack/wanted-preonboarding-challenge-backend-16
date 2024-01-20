package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;
    @Column(name = "reservation_status")
    private String reservationStatus;
    @Column(nullable = false)
    private int round;
    private int gate;
    private char line;
    private int seat;

    public static Reservation of(ReserveInfo info) {
        return Reservation.builder()
            .performanceId(info.getPerformanceId())
            .name(info.getReservationName())
            .phoneNumber(info.getReservationPhoneNumber())
            .reservationStatus(info.getReservationStatus())
            .round(info.getRound())
            .gate(1)
            .line(info.getLine())
            .seat(info.getSeat())
            .build();
    }

}
