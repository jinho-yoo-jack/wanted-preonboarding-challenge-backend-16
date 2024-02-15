package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.core.domain.exception.CustomException;
import com.wanted.preonboarding.ticket.domain.exception.TicketErrorCode;
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
public class PerformanceSeatInfo extends BaseEntity {

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

    public void reserveSeat(){
        if(!isReservable){
            throw new CustomException(TicketErrorCode.PERFORMANCE_SEAT_NOT_RESERVABLE);
        }
        isReservable = false;
    }

    public void cancelSeat() {
        isReservable = true;
    }
}
