package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.ticket.domain.dto.CreatePerformanceRequestDto;
import com.wanted.preonboarding.ticket.domain.dto.PerformanceType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.sql.Date;
import java.util.UUID;

import static com.wanted.preonboarding.ticket.domain.dto.PerformanceType.fromCategory;

@Entity
@Table
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Performance {
    @Id
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(columnDefinition = "BINARY(16)")
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private int price;
    @Column(nullable = false)
    private int round;
    @Column(nullable = false)
    private PerformanceType type;
    @Column(nullable = false)
    private Date start_date;
    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    private String isReserve;

    public static Performance of(CreatePerformanceRequestDto requestDto){
        return new PerformanceBuilder()
                .name(requestDto.getPerformanceName())
                .price(requestDto.getPrice())
                .round(requestDto.getRound())
                .type(fromCategory(requestDto.getType()))
                .start_date(requestDto.getStart_date())
                .isReserve("enable")
                .build();
    }
}
