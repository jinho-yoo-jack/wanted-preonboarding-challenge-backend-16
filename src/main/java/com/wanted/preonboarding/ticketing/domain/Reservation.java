package com.wanted.preonboarding.ticketing.domain;

import com.wanted.preonboarding.ticketing.domain.vo.ReservedShow;
import com.wanted.preonboarding.ticketing.domain.vo.SeatInfo;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Comment;

@Entity
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Table
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Comment("예약 번호")
    private Long id;

    @Column(nullable = false)
    @Comment("예약자 이름")
    private String customerName;

    @Column(nullable = false)
    @Comment("휴대 전화")
    private String phoneNumber;

    @Column(nullable = false)
    @Comment("잔고")
    private Long balance;

    @Column(nullable = false)
    @Comment("예약한 공연 혹은 전시회 관련 정보")
    private ReservedShow reservedShow;

    @Comment("좌석 정보")
    private SeatInfo seatInfo;
}
