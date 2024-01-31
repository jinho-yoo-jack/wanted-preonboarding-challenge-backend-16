package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.ReservationInfo;
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
    private Integer id;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "user_id")
    private UUID userId;
    @Column(nullable = false)
    private Integer round;
    @Column(nullable = false)
    private Integer gate;
    @Column(nullable = false)
    private Character line;
    @Column(nullable = false)
    private Integer seat;

    public static Reservation of(ReservationInfo info) {
        return Reservation.builder()
                .performanceId(info.getPerformanceInfo().getPerformanceId())
                .userId(info.getUserInfo().getUserId())
                .round(info.getPerformanceInfo().getRound())
                .gate(info.getGate())
                .line(info.getLine())
                .seat(info.getSeat())
                .build();
    }

}
