package com.wanted.preonboarding.ticket.domain.reservation;

import com.wanted.preonboarding.ticket.domain.BaseTimeEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Entity
public class Reservation extends BaseTimeEntity {
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

    @Builder
    public Reservation(UUID performanceId, String name, String phoneNumber, int round, int gate, char line, int seat) {
        this.performanceId = performanceId;
        this.name = name;
        this.phoneNumber = phoneNumber;
        this.round = round;
        this.gate = gate;
        this.line = line;
        this.seat = seat;
    }

}
