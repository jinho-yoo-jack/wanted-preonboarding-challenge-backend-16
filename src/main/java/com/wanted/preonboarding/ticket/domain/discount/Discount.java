package com.wanted.preonboarding.ticket.domain.discount;

import com.wanted.preonboarding.ticket.domain.base.BaseTimeEntity;
import com.wanted.preonboarding.ticket.domain.discount.model.DiscountType;
import com.wanted.preonboarding.ticket.domain.discount.model.DiscountTypeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table
@Entity
public class Discount extends BaseTimeEntity {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(columnDefinition = "BINARY(16)", nullable = false, name = "performance_id")
    private UUID performanceId;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    @Convert(converter = DiscountTypeConverter.class)
    private DiscountType type;

    @Column(nullable = false)
    private Integer amount;

    @Column(nullable = false)
    private LocalDateTime endDate;

    @Builder
    public Discount(UUID performanceId, String name, DiscountType type, Integer amount, LocalDateTime endDate) {
        this.performanceId = performanceId;
        this.name = name;
        this.type = type;
        this.amount = amount;
        this.endDate = endDate;
    }
}
