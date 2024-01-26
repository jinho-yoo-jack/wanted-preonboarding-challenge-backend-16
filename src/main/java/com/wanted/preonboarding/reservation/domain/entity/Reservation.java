package com.wanted.preonboarding.reservation.domain.entity;

import com.wanted.preonboarding.common.model.DefaultEntity;
import com.wanted.preonboarding.common.model.SeatInfo;
import com.wanted.preonboarding.performance.domain.entity.Performance;
import com.wanted.preonboarding.reservation.domain.dto.ReservationRequest;
import com.wanted.preonboarding.reservation.domain.valueObject.UserInfo;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Embedded
    private UserInfo userInfo;

    @Embedded
    private SeatInfo seatInfo;

    public static Reservation from(final ReservationRequest reservationRequest, final Performance performance) {
        return Reservation.builder()
                .performance(performance)
                .seatInfo(SeatInfo.from(reservationRequest))
                .userInfo(UserInfo.of(reservationRequest.getName(), reservationRequest.getPhoneNumber()))
                .build();
    }
}