package com.wanted.preonboarding.ticketing.domain.entity;

import com.wanted.preonboarding.ticketing.service.dto.Discount;
import com.wanted.preonboarding.ticketing.controller.request.CreateReservationRequest;
import com.wanted.preonboarding.ticketing.controller.response.CreateReservationResponse;
import com.wanted.preonboarding.ticketing.controller.response.ReadReservationResponse;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.Comment;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
@Getter
public class Reservation extends Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("ID")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id", columnDefinition = "BINARY(16)", nullable = false)
    @Comment("공연전시ID")
    private Performance performance;

    @Column(nullable = false)
    @Comment("예약자명")
    private String name;

    @Column(nullable = false)
    @Comment("예약자 휴대전화 번호")
    private String phoneNumber;

    @Column(nullable = false)
    @Comment("회차(FK)")
    private int round;

    @Column(nullable = false)
    @Comment("입장 게이트")
    private int gate;

    @Column(nullable = false)
    @Comment("좌석 열")
    private String line;

    @Column(nullable = false)
    @Comment("좌석 행")
    private int seat;

    public static Reservation from(CreateReservationRequest createReservationRequest, Performance performance) {
        return createReservationRequest.fromTicket(performance);
    }

    public CreateReservationResponse toCreateReservationResponse(Performance performance, Discount discount) {
        return CreateReservationResponse.builder()
                .performanceId(this.performance.getId())
                .performanceName(performance.getName())
                .round(this.round)
                .gate(this.gate)
                .line(this.line)
                .seat(this.seat)
                .discount(discount)
                .reservationName(this.name)
                .phoneNumber(this.phoneNumber)
                .build();
    }

    public ReadReservationResponse toReadReservationResponse() {
        return ReadReservationResponse.builder()
                .performanceId(this.performance.getId())
                .performanceName(this.performance.getName())
                .gate(this.gate)
                .line(this.line)
                .seat(this.seat)
                .round(this.round)
                .reservationName(this.name)
                .phoneNumber(this.phoneNumber)
                .build();
    }
}
