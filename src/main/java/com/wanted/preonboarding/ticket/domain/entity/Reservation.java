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
@NoArgsConstructor
@AllArgsConstructor
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
    private String status;
    @Column(nullable = false, columnDefinition = "int default 0")
    private long amount;
    @Column(nullable = false)
    private int round;
    @Column(nullable = false)
    private int gate;
    @Column(nullable = false)
    private char line;
    @Column(nullable = false)
    private int seat;

    public static Reservation of(ReserveInfo info){
        return Reservation.builder()
                .performanceId(info.getPerformanceId())
                .name(info.getReservationName())
                .phoneNumber(info.getReservationPhoneNumber())
                .status(info.getReservationStatus())
                .amount(info.getAmount())
                .round(info.getRound())
                .gate(1)
                .line(info.getLine())
                .seat(info.getSeat())
                .build();
    }

}
