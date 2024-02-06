package com.wanted.preonboarding.ticket.domain.entity;

import com.wanted.preonboarding.account.domain.vo.Money;
import com.wanted.preonboarding.core.domain.support.entity.BaseEntity;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(uniqueConstraints = { @UniqueConstraint(name = "performance_unique",
        columnNames = {"id", "round"})})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Performance extends BaseEntity {

    public final static String ENABLE = "enable";
    public final static String DISABLE = "disable";

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

    @Column(nullable = false, name = "start_date")
    private LocalDateTime startDate;

    @Column(nullable = false, name = "is_reserve", columnDefinition = "varchar default 'disable'")
    private String isReserve;

    @Builder
    private Performance(UUID id, String name, int price, int round, int type, LocalDateTime startDate, String isReserve) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.round = round;
        this.type = type;
        this.startDate = startDate;
        this.isReserve = isReserve;
    }

    public static Performance of(UUID id, String name, int price, int round, int type,
                                 LocalDateTime startDate, String isReserve) {
        return Performance.builder()
                .id(id)
                .name(name)
                .price(price)
                .round(round)
                .type(type)
                .startDate(startDate)
                .isReserve(isReserve)
                .build();
    }

    public boolean isPossibleReserve() {
        return isReserve.equals(ENABLE);
    }

    public Money getPrice() {
        return Money.createMoney(BigDecimal.valueOf(price));
    }
}
