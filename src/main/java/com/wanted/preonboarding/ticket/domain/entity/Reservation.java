package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    @Column(nullable = false, name = "created_at")
    private Timestamp createdAt;

    public static Reservation of(ReserveInfo info) {
        return Reservation.builder()
                .performanceId(info.getPerformanceId())
                .userId(info.getUserInfo().getUserId())
                .round(info.getRound())
                .gate(1)
                .line(info.getLine())
                .seat(info.getSeat())
                .build();
    }

}
