package com.wanted.preonboarding.reservation.domain.entity;

import com.wanted.preonboarding.common.model.DefaultEntity;
import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.reservation.domain.dto.ReservationRequest;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Reservation extends DefaultEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @Column(name = "performance_id")
//    private Performance performance;
    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, name = "phone_number")
    private String phoneNumber;

    @Embedded
    private SeatInfo seatInfo;

    public static Reservation from(ReservationRequest reservationRequest) {
        return Reservation.builder()
                .name(reservationRequest.getName())
                .phoneNumber(reservationRequest.getPhoneNumber())
                .performanceId(reservationRequest.getPerformanceId())
                .seatInfo(SeatInfo.from(reservationRequest))
                .build();
    }
}