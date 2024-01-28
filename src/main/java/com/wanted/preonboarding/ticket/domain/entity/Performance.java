package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.exception.BadRequestException;
import com.wanted.preonboarding.ticket.domain.exception.NotFoundException;
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
            throw new BadRequestException("예약할 수 없는 공연입니다.");
        }
        getSeat(seatInfo).reserveSeat();
    }

    private PerformanceSeatInfo getSeat(SeatInfo seatInfo){
        return seatInfos.stream()
                .filter(s -> s.isSeatInfoEqual(seatInfo))
                .findAny()
                .orElseThrow(() -> new NotFoundException("좌석이 존재하지 않습니다."));
    }

}
