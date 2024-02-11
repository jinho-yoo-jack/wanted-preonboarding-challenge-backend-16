package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.request.CreateReservationRequest;
import com.wanted.preonboarding.ticket.global.common.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@Entity
@Table
@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
public class Reservation extends BaseEntity {
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

    public static Reservation from(CreateReservationRequest createReservationRequest) {
        return Reservation.builder()
            .performanceId(createReservationRequest.getPerformanceId())
            .name(createReservationRequest.getReservationName())
            .phoneNumber(createReservationRequest.getReservationPhoneNumber())
            .round(createReservationRequest.getRound())
            .gate(createReservationRequest.getGate())
            .line(createReservationRequest.getLine())
            .seat(createReservationRequest.getSeat())
            .build();
    }

}
