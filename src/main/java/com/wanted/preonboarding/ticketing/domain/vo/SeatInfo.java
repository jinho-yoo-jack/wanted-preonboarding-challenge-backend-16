package com.wanted.preonboarding.ticketing.domain.vo;

import jakarta.persistence.Embeddable;
import org.hibernate.annotations.Comment;

@Embeddable
public class SeatInfo {
    @Comment("좌석 번호")
    private int no;

    @Comment("좌석 예약 가능 여부")
    private Boolean IsReserved;
}
