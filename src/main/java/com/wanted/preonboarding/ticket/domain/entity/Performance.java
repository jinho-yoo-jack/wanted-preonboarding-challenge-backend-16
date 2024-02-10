package com.wanted.preonboarding.ticket.domain.entity;

import static lombok.AccessLevel.PROTECTED;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;

import java.util.Date;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
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
    private int type;

    @Column(nullable = false)
    private Date start_date;

    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar(255) default 'disable'")
    private String isReserve;

    @OneToMany(mappedBy = "performance")
    private List<PerformanceSeatInfo> performanceSeatInfos = new ArrayList<>();

    public static Performance create(String name, int price, int round, int type, Date start_date, String isReserve) {
        Performance performance = new Performance();
        performance.name = name;
        performance.price = price;
        performance.round = round;
        performance.type = type;
        performance.start_date = start_date;
        performance.isReserve = isReserve;
        return performance;
    }
}
