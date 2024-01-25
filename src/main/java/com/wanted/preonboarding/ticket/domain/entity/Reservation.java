package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.performance.domain.Performance;
import com.wanted.preonboarding.ticket.domain.dto.ReserveInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, name = "performance_id")
    private Performance performance;
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
            .performance(Performance.builder().build())
            .name(info.getReservationName())
            .phoneNumber(info.getReservationPhoneNumber())
            .round(info.getRound())
            .gate(1)
            .line(info.getLine())
            .seat(info.getSeat())
            .build();
    }

}
