package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CanceledReservation {
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
    @Column(nullable = false, name = "canceled_at")
    private Timestamp canceledAt;
}
