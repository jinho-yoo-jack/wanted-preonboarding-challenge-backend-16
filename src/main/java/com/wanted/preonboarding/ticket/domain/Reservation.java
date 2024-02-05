package com.wanted.preonboarding.ticket.domain;

import com.wanted.preonboarding.core.code.ReservationStatus;
import com.wanted.preonboarding.ticket.domain.base.BaseEntity;
import com.wanted.preonboarding.ticket.service.discount.DiscountPolicy;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;


@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "reservation")
public class Reservation extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id", updatable = false)
    private Long id;

    @Column(name = "title", updatable = false, nullable = false)
    private String title;

    @Column(name = "round", updatable = false, nullable = false)
    private int round;

    @Column(name = "amount", updatable = false, nullable = false)
    private double amount;

    @Column(name = "user_name", length = 100, nullable = false)
    private String userName;

    @Column(name = "phone_number", length = 20, nullable = false)
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 10, nullable = false)
    private ReservationStatus status;

    @OneToOne(fetch = FetchType.LAZY)
    private Performance performance;

    @OneToOne(fetch = FetchType.LAZY)
    private PerformanceSeat performanceSeat;

    public static Reservation create(String userName, String phoneNumber, Performance performance, PerformanceSeat performanceSeat) {
        return Reservation.builder()
                .userName(userName)
                .phoneNumber(phoneNumber)
                .title(performance.getTitle())
                .round(performance.getRound())
                .amount(performance.getPrice())
                .status(ReservationStatus.APPLY)
                .performance(performance)
                .performanceSeat(performanceSeat)
                .build();
    }

    public void cancel() {
        status = ReservationStatus.CANCEL;
    }

    public void discount(DiscountPolicy discountPolicy) {
        amount = discountPolicy.discount(amount);
    }
}
