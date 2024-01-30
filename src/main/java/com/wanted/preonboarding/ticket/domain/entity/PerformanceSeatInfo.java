package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.exception.BadRequestException;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name="performance_seat_info", uniqueConstraints = {
        @UniqueConstraint(name = "performance_seat_info_unique", columnNames = {"performance_id", "round", "line", "seat"})
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PerformanceSeatInfo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "performance_id")
    private Performance performance;

    @Embedded
    private SeatInfo seatInfo;

    @Column(name="is_reserve")
    @Convert(converter = EnableDisableConverter.class)
    private Boolean isReservable;

    public boolean isSeatInfoEqual(SeatInfo seatInfo) {
        return this.seatInfo.getRound().equals(seatInfo.getRound())
                && this.seatInfo.getGate().equals(seatInfo.getGate())
                && this.seatInfo.getLine().equals(seatInfo.getLine())
                && this.seatInfo.getSeat().equals(seatInfo.getSeat());
    }

    public void reserveSeat(){
        if(!isReservable){
            throw new BadRequestException("예약할 수 없는 좌석입니다.");
        }
        isReservable = false;
    }

    public void cancelSeat() {
        isReservable = true;
    }
}
