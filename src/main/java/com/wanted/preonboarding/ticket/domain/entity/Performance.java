package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.core.domain.exception.CustomException;
import com.wanted.preonboarding.ticket.domain.exception.TicketErrorCode;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="performance", uniqueConstraints = {
        @UniqueConstraint(name = "performance_unique", columnNames = {"id", "round"})
})
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance extends BaseEntity {

    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name="uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;

    private String name;

    private Integer price;

    private Integer round;

    private PerformanceType type;

    private LocalDateTime startDate;

    @Column(name="is_reserve", columnDefinition = "varchar default 'disable'")
    @Convert(converter = EnableDisableConverter.class)
    private Boolean isReservable;
    
    @OneToMany(mappedBy = "performance")
    private List<PerformanceSeatInfo> seatInfos;

    public void reserveSeat(SeatInfo seatInfo) {
        if(!isReservable){
            throw new CustomException(TicketErrorCode.PERFORMANCE_NOT_RESERVABLE);
        }
        getSeat(seatInfo).reserveSeat();
    }

    private PerformanceSeatInfo getSeat(SeatInfo seatInfo){
        return seatInfos.stream()
                .filter(s -> s.getSeatInfo().equals(seatInfo))
                .findAny()
                .orElseThrow(() -> new CustomException(TicketErrorCode.PERFORMANCE_SEAT_NOT_FOUND));
    }

    public void cancelSeat(SeatInfo seatInfo) {
        getSeat(seatInfo).cancelSeat();
    }
}
