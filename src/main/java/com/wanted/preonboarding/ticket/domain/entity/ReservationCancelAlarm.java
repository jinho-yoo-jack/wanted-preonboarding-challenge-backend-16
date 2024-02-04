package com.wanted.preonboarding.ticket.domain.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@NoArgsConstructor
@Entity
public class ReservationCancelAlarm {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String performanceId;
    private String phoneNumber;
    private String name;

    @Builder
    public ReservationCancelAlarm(String performanceId, String phoneNumber, String name) {
        this.performanceId = performanceId;
        this.phoneNumber = phoneNumber;
        this.name = name;
    }
}
