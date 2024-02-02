package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
            .round(info.getRound())
            .gate(1)
            .line(info.getLine())
            .seat(info.getSeat())
            .build();
    }

}
